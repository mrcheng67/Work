package com.jinan.Configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

// SpringMVC的配置类
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    // Configure ViewResolver
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/"); // JSP 文件的路径，确保这与 application.yml 中的配置一致
        resolver.setSuffix(".jsp"); // JSP 文件的后缀
        return resolver;
    }

    // Optional: You can also register DispatcherServlet here if needed
    // Spring Boot will automatically create a DispatcherServlet for you
    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }
}
