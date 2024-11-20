package com.jinan.service.UserLogin.impl;


import com.jinan.entities.UserLogin.Login;
import com.jinan.mapper.UserLogin.LoginMapper;
import com.jinan.service.UserLogin.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public Login findUserByName(String name) {
        return loginMapper.findUserByName(name);
    }

    @Override
    public Login loginSuccess(String username, String passwd) {
        return loginMapper.loginSuccess(username, passwd);
    }
}
