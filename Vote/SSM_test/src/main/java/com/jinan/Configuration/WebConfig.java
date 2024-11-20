package com.jinan.Configuration;

//import com.jinan.obstacle.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.*;
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

//    @Autowired
//    private LoginCheckInterceptor loginCheckInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //注册自定义拦截器对象
//        registry.addInterceptor(loginCheckInterceptor)
//                .addPathPatterns("")//设置拦截器拦截的请求路径（ /** 表示拦截所有请求）
//                .excludePathPatterns("/");//设置不拦截的请求路径
//    }

    // 定义了跨域问题，使得可以设置请求头 "Authorization", "Token"
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")//允许的头信息
                        .allowedMethods("*")
                        .allowedOrigins("http://localhost:8080")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }


}
