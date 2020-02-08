package cn.edu.ccnu.kolibreath.al_viewer.controller;

import cn.edu.ccnu.kolibreath.al_viewer.Constants;
import cn.edu.ccnu.kolibreath.al_viewer.algorithm.ImprovedBatAlgorithm;
import cn.edu.ccnu.kolibreath.al_viewer.algorithm.OriginalBatAlgorithm;
import cn.edu.ccnu.kolibreath.al_viewer.algorithm.functions.ImplementedFunctions;
import cn.edu.ccnu.kolibreath.al_viewer.model.*;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;

//生成和返回图片的情况粒子分布等等
@RestController
@RequestMapping("graph")
public class GraphController {

    private double lower[] = {-10.0, -10.0, -10.0};
    private double upper[] = {10.0,10.0,10.0};
    private int n = 20;//population size
    private int Ngen = 1000; // number of generation
    private double  A = 0.45; // loudness
    private double r =0.5;
    private double Qmin = 0.0;
    private double Qmax = 2.0;
    //默认情况下是0.01秒一次迭代 1秒钟进行100次迭代
    private double speed = 0.01;

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
     * @return
     */
    @PostMapping("/api/start/{id}")
    public ResultBean postStart(@PathVariable("id") int id){
        functions.setIndex(id);
        OriginalBatAlgorithm origin = new OriginalBatAlgorithm(functions, n , Ngen , A, r ,Qmin, Qmax, lower, upper);
        ImprovedBatAlgorithm improved = new ImprovedBatAlgorithm(functions, n , Ngen , A, r ,Qmin, Qmax, lower, upper );

//        double [][] originResult = origin.solutionEachGeneration(tWrapper);
//        double [][] improvedResult = improved.solutionEachGeneration(tWrapper);

        return ResultBean.success(null);
    }

    /**
     * 每次调用返回当前的粒子状态
     * id = 当前调用的测试函数类型
     * @return 当前的粒子参数
     */
    @RequestMapping("/original/{id}")//todo function
    public ResultBean getOriginalParticles(HttpSession httpSession , @PathVariable("id") int id){
        functions.setIndex(id);
        OriginalBatAlgorithm algorithm = new OriginalBatAlgorithm(functions, n ,Ngen , A , r, Qmin,Qmax, lower, upper);
        int tWrapper[] = new int[1];
        tWrapper[0] = ((UserSession) httpSession.getAttribute(Constants.USER_SESSION)).getGeneration();
//        double solutionResult[][] = algorithm.solutionEachGeneration(tWrapper);
//        List<Particle> particleList = array2Particles(solutionResult);
//        return ResultBean.success(particleList);
        return null;
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
