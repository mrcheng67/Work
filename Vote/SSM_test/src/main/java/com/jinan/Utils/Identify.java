package com.jinan.Utils;

import com.jinan.Configuration.JwtConfig;
import com.jinan.entities.UserLogin.Login;
import com.jinan.service.UserLogin.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Identify {
    private static JwtConfig jwt = new JwtConfig();

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    LoginService loginService;

    public Login Login(String username, String password){
        Login is = loginService.loginSuccess(username,password);
        return is;
    }

    // 对用户名密码进行Jwt加密
    public String JwtSyn(String getUsername, String getPasswd, String getName){
        Map<String , Object> claims = new HashMap<>();
        claims.put("username", getUsername);
        claims.put("password", getPasswd);
        claims.put("name", getName);
        String jwt = JwtConfig.generateJwt(claims);
        redisTemplate.opsForValue().set("login"+getName,jwt);
        return jwt;
    }
    public String GetRealName(String token){
        Map<String, Object> voterName = jwt.parseJWT(token);
        return (String) voterName.get("name");
    }
    public String GetName(String token){
        Map<String, Object> voterName = jwt.parseJWT(token);
        return (String) voterName.get("username");
    }

    public static String GetPasswd(String token){
        Map<String, Object> voterName = jwt.parseJWT(token);
        return (String) voterName.get("password");
    }

}
