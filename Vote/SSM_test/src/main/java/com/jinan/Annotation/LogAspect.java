package com.jinan.Annotation;

import java.lang.reflect.Method;
import com.jinan.Annotation.LogAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class LogAspect{

    @Pointcut("@annotation(com.jinan.Annotation.LogAnnotation)")
    public void logPointCut(){

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Date now = new Date();
        BufferedWriter bf = new BufferedWriter(new FileWriter("log.txt"));

        String name = point.getSignature().getName();
        MethodSignature methodsignature = (MethodSignature) point.getSignature();
        Method method = methodsignature.getMethod();
        LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
        if(annotation != null){
            String value = annotation.value();
            bf.write("[日志文件] ["+ now.getTime() +"] {调用了" + name + "方法 , 当前操作为: "  + value + " 返回值是 : "+ point.proceed() +"]");
            System.out.println("调用了" + name + "方法 , 当前操作为: " );
        }
        return point.proceed();
    }

    @Before("execution(* com.jinan.service.User.impl.*.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        // 获取连接点的签名信息
        Signature signature = joinPoint.getSignature();

        // 获取方法名
        String methodName = signature.getName();

        // 打印方法信息
        System.out.println("方法名：" + methodName);

    }

}
/*
*
        * */