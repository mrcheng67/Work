package com.jinan;

import com.jinan.controller.EmployeeController;
import com.jinan.service.EmployeeService;
import com.jinan.service.LoginService;
import com.jinan.service.impl.EmployeeServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;


@SpringBootApplication
public class Application {
    public static void main(String[] aegs){
        ApplicationContext app = new ClassPathXmlApplicationContext("beans.xml");
        LoginService service = app.getBean(LoginService.class);
        EmployeeService controller = app.getBean(EmployeeService.class);
        controller.getEmployee();
        System.out.println(service.loginSuccess("mrcheng","123"));
    }
}
