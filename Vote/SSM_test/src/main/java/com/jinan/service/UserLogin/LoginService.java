package com.jinan.service.UserLogin;

import com.jinan.entities.UserLogin.Login;

public interface LoginService {

    Login findUserByName(String name);

    Login loginSuccess(String username,String password);
}
