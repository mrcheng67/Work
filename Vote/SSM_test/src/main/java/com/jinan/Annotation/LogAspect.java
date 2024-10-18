package com.jinan.Annotation;



import java.io.IOException;
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
    public void logPointCut() {
        // Pointcut方法，可以在这里记录调试信息
    }

    @Around("@annotation(LogAnnotation)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Date now = new Date();
        Object result = null;  // 保存返回值

        // 使用 try-with-resources 确保 BufferedWriter 会正确关闭
        try (BufferedWriter bf = new BufferedWriter(new FileWriter("log.txt", true))) {
            String name = point.getSignature().getName();
            MethodSignature methodSignature = (MethodSignature) point.getSignature();
            Method method = methodSignature.getMethod();
            LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);

            if (annotation != null) {
                String value = annotation.value();
                try {
                    // 调用目标方法并获取返回值
                    result = point.proceed();

                    // 写日志
                    bf.write("[日志文件] [" + now.getTime() + "] {调用了 " + name + " 方法 , 当前操作为: " + value + " 返回值是 : " + result + "}\n");

                    // 输出日志到控制台
                    System.out.println("调用了 " + name + " 方法 , 当前操作为: " + value + " 返回值是 : " + result);
                } catch (Throwable throwable) {
                    // 处理异常，写错误日志
                    bf.write("[错误日志] [" + now.getTime() + "] 方法" + name + " 执行失败, 异常信息: " + throwable.getMessage() + "\n");
                    throw throwable; // 继续抛出异常以便后续处理
                }
            }
        } catch (IOException e) {
            System.err.println("写日志时发生IO异常: " + e.getMessage());
        }

        return result; // 返回目标方法的返回值
    }

    @Before("execution(* com.jinan.service.impl.*.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        // 获取连接点的签名信息
        Signature signature = joinPoint.getSignature();
        // 获取方法名
        String methodName = signature.getName();
        // 打印方法信息
        System.out.println("方法名： " + methodName + "\t签名信息" + signature);
    }
}
