package com.jinan.mapper.UserLogin;

import com.jinan.entities.UserLogin.Login;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface LoginMapper {

    Login findUserByName(@Param("username") String username);

    Login loginSuccess(@Param("username") String username, @Param("passwd") String passwd);
}
