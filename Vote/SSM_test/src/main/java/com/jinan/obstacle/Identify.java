package com.jinan.obstacle;

import com.jinan.Configuration.JwtConfig;
import com.jinan.entities.UserLogin.Login;
import com.jinan.service.UserLogin.LoginService;
import com.oracle.wls.shaded.org.apache.xpath.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static java.awt.SystemColor.info;

@Component

public class Identify {
    private static JwtConfig jwt = new JwtConfig();

    @Autowired
    LoginService loginService;

    public Login Login(String username, String password){
        Login is = loginService.loginSuccess(username,password);
        return is;
    }

    // 对用户名密码进行Jwt加密
    public static String JwtSyn(String getUsername, String getPasswd, String getName){
        Map<String , Object> claims = new HashMap<>();
        claims.put("username", getUsername);
        claims.put("password", getPasswd);
        claims.put("name", getName);
        return JwtConfig.generateJwt(claims);
    }

    public static String GetName(String token){
        Map<String, Object> voterName = jwt.parseJWT(token);
        return (String) voterName.get("username");
    }

    public static String GetPasswd(String token){
        Map<String, Object> voterName = jwt.parseJWT(token);
        return (String) voterName.get("password");
    }

}
