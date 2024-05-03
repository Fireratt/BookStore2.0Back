package com.example.myapp ; 
import org.springframework.context.annotation.Configuration ; 
import org.springframework.context.annotation.Bean ; 
import com.example.myapp.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
@Configuration
public class FilterRegistration {
    // register the filter in the CorsFilter
    @Bean
    public FilterRegistrationBean registerFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.addUrlPatterns("/*");
        bean.setFilter(new CorsFilter());
        return bean;
    }
}