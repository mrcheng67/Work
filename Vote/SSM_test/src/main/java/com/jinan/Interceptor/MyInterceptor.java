package com.jinan.Interceptor;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 拦截器类
@Component
public class MyInterceptor implements HandlerInterceptor {
    // 请求到达Controller前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("请求到达Controller前");
        // 如果return false则无法到达Controller
        return true;
    }

    // 跳转到JSP前执行，此时可以向Request域添加数据
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("跳转到JSP前");
        request.setAttribute("name","zb");
    }

    // 跳转到JSP后执行，此时已经不能向Request域添加数据
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("跳转到JSP后");
        request.setAttribute("age",10);
    }
}

