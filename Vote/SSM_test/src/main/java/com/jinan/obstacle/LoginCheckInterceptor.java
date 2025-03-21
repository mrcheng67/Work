//package com.jinan.obstacle;
//
//import com.alibaba.fastjson2.JSONObject;
//import com.jinan.Configuration.JwtConfig;
//import com.jinan.entities.UserLogin.Result;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import java.util.Enumeration;
//
//@EnableAspectJAutoProxy
////自定义拦截器
//@Component //当前拦截器对象由Spring创建和管理
//@Slf4j
//public class LoginCheckInterceptor implements HandlerInterceptor {
//    //前置方式
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            String headerValue = request.getHeader(headerName);
//            System.out.println("请求头信息： "+headerName + ": " + headerValue);
//        }
//        System.out.println("Url = " + request.getRequestURL());
//        //1.获取请求url
//        //2.判断请求url中是否包含login，如果包含，说明是登录操作，放行
//
//        //3.获取请求头中的令牌（token）
//        String token = request.getHeader("Authorization");
//        System.out.println("token " + token);
//        log.info("从请求头中获取的令牌：{}", token);
//        //4.判断令牌是否存在，如果不存在，返回错误结果（未登录）
//        if (!StringUtils.hasLength(token)) {
//            log.info("Token不存在");
//
//            //创建响应结果对象
//            Result responseResult = Result.error("NOT_LOGIN");
//            //把Result对象转换为JSON格式字符串 (fastjson是阿里巴巴提供的用于实现对象和json的转换工具类)
//            String json = JSONObject.toJSONString(responseResult);
//            //设置响应头（告知浏览器：响应的数据类型为json、响应的数据编码表为utf-8）
//            response.setContentType("application/json;charset=utf-8");
//            //响应
//            response.getWriter().write(json);
//
//            return false;//不放行
//        }
//
//        //5.解析token，如果解析失败，返回错误结果（未登录）
//        try {
//            JwtConfig.parseJWT(token);
//        } catch (Exception e) {
//            log.info("令牌解析失败!");
//
//            //创建响应结果对象
//            Result responseResult = Result.error("NOT_LOGIN");
//            //把Result对象转换为JSON格式字符串 (fastjson是阿里巴巴提供的用于实现对象和json的转换工具类)
//            String json = JSONObject.toJSONString(responseResult);
//            //设置响应头
//            response.setContentType("application/json;charset=utf-8");
//            //响应
//            response.getWriter().write(json);
//
//            return false;
//        }
//
//        //6.放行
//        return true;
//    }
//}