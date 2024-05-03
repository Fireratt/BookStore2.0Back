package com.example.myapp ; 
import java.util.*;
import jakarta.servlet.* ;            // ServletResponse and Filter...
import jakarta.servlet.http.* ;       // httpServletResponse...
import org.springframework.http.* ; 
import org.springframework.context.annotation.Configuration ; 
import org.springframework.web.server.WebFilter ; 
import java.io.* ; 
// @WebFilter(filterName = "corsFilter", urlPatterns = {"/*"})
public class CorsFilter implements jakarta.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        // cant be "*" for cors with credencials
        response.setHeader("Access-Control-Allow-Credentials" , "true") ; 
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        // response.setHeader("Access-Control-Allow-Headers", "*");
        chain.doFilter(req, response);
    }
    @Override
    public void destroy() {
    }
}
