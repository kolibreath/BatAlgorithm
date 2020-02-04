package cn.edu.ccnu.kolibreath.al_viewer;

import cn.edu.ccnu.kolibreath.al_viewer.model.UserSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@SpringBootApplication
public class AlViewerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlViewerApplication.class, args);
    }

}
