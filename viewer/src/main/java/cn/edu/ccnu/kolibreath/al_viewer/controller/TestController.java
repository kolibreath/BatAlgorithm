package cn.edu.ccnu.kolibreath.al_viewer.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("test")
public class TestController {
    @RequestMapping("/1")
    public String index(){
        return "index2";
    }
}
