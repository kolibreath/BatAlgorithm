package cn.edu.ccnu.kolibreath.al_viewer.controller;

import cn.edu.ccnu.kolibreath.al_viewer.algorithm.ImprovedBatAlgorithm;
import cn.edu.ccnu.kolibreath.al_viewer.algorithm.OriginalBatAlgorithm;
import cn.edu.ccnu.kolibreath.al_viewer.algorithm.functions.ImplementedFunctions;
import cn.edu.ccnu.kolibreath.al_viewer.model.*;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

//生成和返回图片的情况粒子分布等等
@RestController
public class GraphController {

    private double lower[] = {-10, -10, -10};
    private double upper[] = {10.0,10.0,10.0};
    private int n = 20;//population size
    private int Ngen = 1000; // number of generation
    private double  A = 0.45; // loudness
    private double r =0.5;    //pulseRate default Value
    private double Qmin = 0.0;
    private double Qmax = 2.0;  //maximum of frequency


    private double alfa = 0.5264;
    private double gamma = 4.411;

    private double speed;


    private ImplementedFunctions functions = new ImplementedFunctions();

    private ConfigWrapper configWrapper = new ConfigWrapper();


    private OriginalBatAlgorithm original;
    private ImprovedBatAlgorithm improved;


    private String[] functionNames = {
            "sphere",
            "schwefel",
            "schwefel2",
            "schwefel3",
            "Rosen Brock",
            "step",
            "quartic with Noise",
            "schwefel4",
            "Rastrign",
            "Ackley",
            "griewank",
            "penalized",
            "penalized2"};

    private List<IterationWrapper> iterationWrapperList = new LinkedList<>();
    //当前迭代的轮数记录
    public int[] tWrapper = {0};

    //pulseRate 不同的蝙蝠的pulseRate不同
    private double[] pulseRate;

    private List<Particle> array2Particles(double solutionResult[][]) {
        List<Particle> particleList = new LinkedList<>();
        for (int i = 0; i <  solutionResult.length; i++) {
            Particle particle = new Particle();
            particle.setElite(false);
            particle.setCoodinate(solutionResult[i]);
            particleList.add(particle);
        }
        return particleList;
    }


    @RequestMapping(value = "/api/dashboard", method = RequestMethod.GET)
    public ResultBean getParticleDashboardData(){
        return ResultBean.success(iterationWrapperList);
    }

    private double findMax(List<Particle> particles){
        double max = 0;
        for(Particle particle: particles){
            double d[] = {particle.getX(), particle.getY(), particle.getZ()};
            double score = functions.func(d);
            if( score >= max){
                max = score;
            }
        }
        return max;
    }

    private double findMin(List<Particle> particles){
        double min = 0;
        for(Particle particle: particles){
            double d[] = {particle.getX(), particle.getY(), particle.getZ()};
            double score = functions.func(d);
            if( score < min){
                min = score;
            }
        }
        return min;
    }

    private double findAve(List<Particle> particles){
        double sum = 0;
        for(Particle particle: particles){
            double d[] = {particle.getX(), particle.getY(), particle.getZ()};
            sum += functions.func(d);
        }
        return  sum / particles.size();
    }

    private double findStd(List<Particle> particles){
        double ave = findAve(particles);
        double sum = 0;
        for(Particle particle: particles){
            double d[] = {particle.getX(), particle.getY(), particle.getZ()};
            sum += (functions.func(d) - ave) * (functions.func(d) - ave);
        }
        return Math.sqrt(sum / particles.size());
    }

    private void initConfig(int id){
        // 初始化配置
        configWrapper.setPopulation(n);
        configWrapper.setGeneration(Ngen);
        configWrapper.setPulseRate(r);
        configWrapper.setFunctionQueue(new LinkedList<>());
        configWrapper.setFunctionIndex(id);
        configWrapper.setLoudness(A);
        configWrapper.setFrequency(Qmax);
        configWrapper.setSpeed(speed);
    }

    /**
     * 初始化算法的一些值 并将这些值作为 公共领域内的引用
     * @return
     */
    @RequestMapping(value ="/api/init/{id}", method = RequestMethod.POST)
    public ResultBean postInit(@PathVariable("id") int id){
        if(id > functionNames.length) return ResultBean.error(400,"没有这个测试函数");

        functions.setIndex((id));
        original = new OriginalBatAlgorithm(functions, n ,Ngen, A, r, Qmin, Qmax, lower, upper);
        improved = new ImprovedBatAlgorithm(functions, n ,Ngen, A, r, Qmin, Qmax, lower, upper);

        original.initialize();
        improved.initialize();

        pulseRate = new double[n];
        for (int i = 0; i < n; i++) {
            pulseRate[i] = Math.random();
        }

         initConfig(id);

        return ResultBean.success(null);
    }


    /**
     * 每次请求都返回原始蝙蝠算法和改进蝙蝠算法的粒子的位置
     * @return 当前的原始蝙蝠算法和改进蝙蝠算法的粒子的位置
     */
    @RequestMapping(value = "/api/start/", method = RequestMethod.POST)
    public ResultBean postStart(){

         int tem[] = {tWrapper[0]};
        //两次执行并做一步 不能够将tWrapper变化两次
        double [][] originResult = original.solutionEachGeneration(tem, pulseRate, gamma, alfa);
        double [][] improvedResult = improved.solutionEachGeneration(tem, pulseRate, gamma, alfa);

        tWrapper[0]++;

        List<Particle> originalParticles = array2Particles(originResult);
        List<Particle> improvedParticles = array2Particles(improvedResult);

        ParticlesWrapper particlesWrapper = new ParticlesWrapper();
        particlesWrapper.setOriginal(originalParticles);
        particlesWrapper.setImproved(improvedParticles);
        particlesWrapper.setIteration(tWrapper[0]);


        if(tWrapper[0] % 100 == 0 || tWrapper[0] ==999) {
            IterationWrapper wrapper = new IterationWrapper();
            if(tWrapper[0] == 999)
                wrapper.setIteration(1000);
            else
                wrapper.setIteration(tWrapper[0]);

            wrapper.setImprovedMin(findMin(improvedParticles));
            wrapper.setOriginalMin(findMin(originalParticles));

            wrapper.setImprovedAve(findAve(improvedParticles));
            wrapper.setOriginalAve(findAve(originalParticles));

            wrapper.setOriginalMax(findMax(originalParticles));
            wrapper.setImprovedMax(findMax(improvedParticles));

            wrapper.setOriginalStd(findStd(originalParticles));
            wrapper.setImprovedStd(findStd(improvedParticles));

            iterationWrapperList.add(wrapper);
        }


        return ResultBean.success(particlesWrapper);
    }



    /**
     * 返回当前算法的参数
     * @return
     */
    @RequestMapping("/api/curConfig")
    public ResultBean getDefaultConfiguration(){
        return ResultBean.success(configWrapper);
    }

    /**
     * 修改算法后的结果
     * @param defaultConfigWrapper 修改算法的结果 这样的情况下不修改算法的速度 默认不变
     * @return
     */
    @RequestMapping(value = "/api/alterConfig" , method = RequestMethod.POST)
    public ResultBean postAlteredConfig(@RequestBody ConfigWrapper defaultConfigWrapper){
        this.configWrapper = defaultConfigWrapper;
        return ResultBean.success(null);
    }

    /**
     * 只修改当前算法的速度
     * @param speedWrapper
     * @return
     */
    @PostMapping("/api/alterSpeed")
    public ResultBean postAlterSpeed(@RequestBody SpeedWrapper speedWrapper){
        this.configWrapper.setSpeed(speedWrapper.getSpeed());
        return ResultBean.success(null);
    }



    @RequestMapping(value = "/api/reset", method = RequestMethod.POST)
    public ResultBean postResetAlgorithm(){
        initConfig(0);
        tWrapper[0] = 0;
        iterationWrapperList = new LinkedList<>();
        //停止算法运行
        return ResultBean.success(null);
    }



}
