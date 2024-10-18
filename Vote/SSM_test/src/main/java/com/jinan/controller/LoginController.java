package com.jinan.controller;

import com.jinan.Annotation.LogAnnotation;
import com.jinan.entities.Login;
import com.jinan.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping("/login")
    @LogAnnotation("--- 登录 ---")
    public String login(String username,String password){
        Login is = loginService.loginSuccess(username,password);
        System.out.println("username = "+username+"     password = "+password);
        if(is != null){
            return "redirect:/getEmployee";
        }
        return "redirect:/";
    }


}
