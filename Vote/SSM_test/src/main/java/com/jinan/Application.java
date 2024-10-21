package com.jinan;

import com.jinan.service.EmployeeService;
import com.jinan.service.LoginService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication()
//@ImportResource({"classpath:beans.xml"})
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
/*
        ApplicationContext app = new ClassPathXmlApplicationContext("beans.xml");
        ApplicationContext bpp = new ClassPathXmlApplicationContext("beans.xml");
        LoginService service = app.getBean(LoginService.class);
        EmployeeService controller = bpp.getBean(EmployeeService.class);
        controller.findUserById(1);
        System.out.println(service.loginSuccess("mrcheng","123"));*/
    }
}
