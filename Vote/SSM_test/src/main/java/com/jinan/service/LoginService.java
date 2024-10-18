package com.jinan.service;

import com.jinan.Annotation.LogAnnotation;
import com.jinan.entities.Login;

public interface LoginService {
    Login loginSuccess(String username,String password);
}
