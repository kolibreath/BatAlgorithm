package cn.edu.ccnu.kolibreath.al_viewer.controller;

import cn.edu.ccnu.kolibreath.al_viewer.Constants;
import cn.edu.ccnu.kolibreath.al_viewer.model.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class BasicRouterController {

    @RequestMapping("/")
    public String go2index(HttpSession httpSession){
        UserSession userSession = new UserSession();
        userSession.setGeneration(0);
        httpSession.setAttribute(Constants.USER_SESSION, userSession);
        return "index";
    }

    @RequestMapping("/2")
    public String go2test(){
        return "index2";
    }
}
