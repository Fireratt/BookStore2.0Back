package com.example.myapp.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
       InterceptorRegistration registration = registry.addInterceptor(interceptor()) ; 

       registration.addPathPatterns("/**") ; 
       registration.excludePathPatterns(
        "/login" 
        ,"/register"
       ) ; 
    }

    @Bean
    public loginInterceptor interceptor()
    {
        return new loginInterceptor() ; 
    }
}
