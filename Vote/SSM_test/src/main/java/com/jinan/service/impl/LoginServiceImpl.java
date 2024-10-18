package com.jinan.service.impl;


import com.jinan.Annotation.LogAnnotation;
import com.jinan.entities.Login;
import com.jinan.mapper.LoginMapper;
import com.jinan.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginSucess;

    @Override
    public Login loginSuccess(String username,String passwd) {
        return loginSucess.loginSuccess(username, passwd);
    }
}
