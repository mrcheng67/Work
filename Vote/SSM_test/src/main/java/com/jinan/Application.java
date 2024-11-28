package com.jinan;

import jakarta.servlet.jsp.jstl.core.Config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.AnnotationConfigUtils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;


//@MapperScan(basePackages = "com.jinan.mapper")
//@ImportResource({"classpath:beans.xml"})
@ServletComponentScan // 开启对Servlet的支持
@SpringBootApplication()
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
/*        // BeanFactory最重要的一个实现类 --> DefaultListableBeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // bean 的定义（class, scope, 初始化, 销毁）
        AbstractBeanDefinition beanDefinition =
                BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        // 将bean定义放入刚刚定义好的beanFactory工厂里面去注册register
        beanFactory.registerBeanDefinition("config", beanDefinition);
        // 给此beanFactory 手动添加一些常用的后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });
        // 拿到beanFactory中所有的Bean name  进行一个打印 看看刚才我们的config bean是否注册成功
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(">>>>>>>>>>" + beanName);
        }

        ApplicationContext app = new ClassPathXmlApplicationContext("beans.xml");
        ApplicationContext bpp = new ClassPathXmlApplicationContext("beans.xml");
        LoginService service = app.getBean(LoginService.class);
        EmployeeService controller = bpp.getBean(EmployeeService.class);
        controller.findUserById(1);
        System.out.println(service.loginSuccess("mrcheng","123"));*/
    }
}
