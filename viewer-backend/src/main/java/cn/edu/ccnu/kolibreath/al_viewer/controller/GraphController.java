package cn.edu.ccnu.kolibreath.al_viewer.controller;

import cn.edu.ccnu.kolibreath.al_viewer.Constants;
import cn.edu.ccnu.kolibreath.al_viewer.algorithm.OriginalBatAlgorithm;
import cn.edu.ccnu.kolibreath.al_viewer.algorithm.functions.ImplementedFunctions;
import cn.edu.ccnu.kolibreath.al_viewer.model.Particle;
import cn.edu.ccnu.kolibreath.al_viewer.model.ResultBean;
import cn.edu.ccnu.kolibreath.al_viewer.model.UserSession;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ImplementedFunctions functions = new ImplementedFunctions();

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
        double solutionResult[][] = algorithm.solutionEachGeneration(tWrapper);
        List<Particle> particleList = new LinkedList<Particle>();
        for (int i = 0; i <  solutionResult.length; i++) {
            Particle particle = new Particle();
            particle.setElite(false);
            particle.setCoodinate(solutionResult[i]);
            particleList.add(particle);
        }
        return ResultBean.success(particleList);
    }
}
