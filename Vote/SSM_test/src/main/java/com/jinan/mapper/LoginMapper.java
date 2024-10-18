package com.jinan.mapper;

import com.jinan.entities.Login;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {
    Login loginSuccess(@Param("username") String username, @Param("passwd") String passwd);
}
