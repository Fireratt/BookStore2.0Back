package com.example.myapp.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.myapp.utils.SessionUtils;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
public class loginInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Pre Handle the interceptor") ;
        try{
            System.out.println("Method in interceptor:" + request.getMethod()) ;            
            if( HttpMethod.OPTIONS.matches(request.getMethod()))    // handle the preflight
            {
                return true ; 
            }
            if(SessionUtils.readSession("user_id", request) != "")
            {
                return true ; 
            }
        }
        catch(Exception err)
        {
            System.out.println(err);
        }
        response.setStatus(401);
        
        return false ; 
    }
    @Override
    public void postHandle(HttpServletRequest request
        , HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("PostHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response
        , Object handler, Exception ex) throws Exception {
        response.setStatus(200);
        System.out.println("afterCompletion");
    }
}
