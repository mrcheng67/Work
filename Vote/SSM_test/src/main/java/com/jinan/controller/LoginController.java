package com.jinan.controller;

import com.jinan.entities.Login;
import com.jinan.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping("/login")
    public String login(String username,String password){
        Login is = loginService.loginSuccess(username,password);
        System.out.println("username = "+username+"     password = "+password);
        if(is != null){
            return "redirect:/getEmployee";
        }
        return "redirect:/";
    }
}
