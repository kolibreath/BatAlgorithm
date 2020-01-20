package cn.edu.ccnu.kolibreath.al_viewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class AlViewerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlViewerApplication.class, args);
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

}
