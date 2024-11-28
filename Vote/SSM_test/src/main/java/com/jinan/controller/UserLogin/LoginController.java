package com.jinan.controller.UserLogin;

import com.jinan.entities.UserLogin.Login;
import com.jinan.Utils.Identify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import com.jinan.entities.UserLogin.Result;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// https://blog.csdn.net/qq_31183297/article/details/79419222 逻辑方法
@Controller
@Slf4j
public class LoginController {

    @Autowired
    Identify identify;
    @GetMapping("/")
    public String home() {
        return "/index";
    }

    @ResponseBody
    @PostMapping(value = "/")
    public Result<? extends Object> login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");//获取前台传入的数据(username) 根据标签name获取
        String password = request.getParameter("password");

        Login is = identify.Login(username,password);
        System.out.println("username = "+username+"     password = "+password);
        if(is != null){
            //使用JWT工具类，生成身份令牌
            String token = Identify.JwtSyn(is.getUsername(),is.getPasswd(),is.getName());

            System.out.println("-- 可以成功登录 --");
            return Result.success(token);
        }
        // 使用@Slf4j注解生成的日志对象


        return Result.error("用户名或密码错误");
    }
}
