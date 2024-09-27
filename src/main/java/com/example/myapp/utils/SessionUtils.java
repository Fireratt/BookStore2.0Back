package com.example.myapp.utils ; 
import jakarta.servlet.http.* ;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.myapp.data.*;
import com.example.myapp.repository.AccessUser;
public class SessionUtils {
    public static void setSession(UserAuth userAuth , HttpServletRequest request) // pass the request , write the session
    {
        HttpSession session = request.getSession() ; 

        session.setAttribute("user_id", userAuth.getUser_id());
    }
    public static String readSession(String attribute,HttpServletRequest request)
    {
        HttpSession session = request.getSession(false) ; 
        Object result = null; 
        if(session != null)
        {
            result = session.getAttribute(attribute) ; 
        }
        if(result == null)
        {
            return "" ; 
        }
        return result.toString() ; 
    }
    public static void clearSession(HttpServletRequest request){
        request.getSession().invalidate(); 
    }
    public class UserBanException extends Exception
    {
        static final String message = "This User Have Been Banned , Session Invalid" ; 
    }
}