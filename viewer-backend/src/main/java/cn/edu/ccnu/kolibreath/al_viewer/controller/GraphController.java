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
    private double r =0.5;
    private double Qmin = 0.0;
    private double Qmax = 2.0;
    //默认情况下是0.01秒一次迭代 1秒钟进行100次迭代
    private double speed = 0.01;

    private double alfa = 0.5264;
    private double gamma = 4.411;


    private ImplementedFunctions functions = new ImplementedFunctions();

    //default configuration
    private DefaultConfigWrapper configWrapper = new DefaultConfigWrapper(
            lower,upper, n, Ngen, A , r, Qmin, Qmax, functions, speed
    );

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

    //当前迭代的轮数记录
    public int[] tWrapper = {0};

    /**
     * 每次请求都返回原始蝙蝠算法和改进蝙蝠算法的粒子的位置
     * @return 当前的原始蝙蝠算法和改进蝙蝠算法的粒子的位置
     */
    @RequestMapping(value = "/api/start/{id}", method = RequestMethod.POST)
    public ResultBean postStart(@PathVariable("id") int id){
        functions.setIndex(id);
        OriginalBatAlgorithm origin = new OriginalBatAlgorithm(functions, n , Ngen , A, r ,Qmin, Qmax, lower, upper);
        ImprovedBatAlgorithm improved = new ImprovedBatAlgorithm(functions, n , Ngen , A, r ,Qmin, Qmax, lower, upper );
        
        origin.initialize();
        improved.initialize();

        double pulseRate[] = new double[n];
        for (int i = 0; i < n; i++) {
            pulseRate[i] = Math.random();
        }
        double [][] originResult = origin.solutionEachGeneration(tWrapper, pulseRate, gamma, alfa);
        double [][] improvedResult = improved.solutionEachGeneration(tWrapper, pulseRate, gamma, alfa);

        List<Particle> originParticles = array2Particles(originResult);
        List<Particle> improvedParticles = array2Particles(improvedResult);

        ParticlesWrapper particlesWrapper = new ParticlesWrapper();
        particlesWrapper.setOriginal(originParticles);
        particlesWrapper.setImproved(improvedParticles);

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
    @PostMapping("/api/alterConfig")
    public ResultBean postAlteredConfig(@RequestBody DefaultConfigWrapper defaultConfigWrapper){
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



    @PostMapping("/api/reset")
    public ResultBean postResultAlgorithm(){
        configWrapper = new DefaultConfigWrapper(
                lower,upper, n, Ngen, A , r, Qmin, Qmax, functions,speed
        );
        //停止算法运行
        return ResultBean.success(null);
    }



}
