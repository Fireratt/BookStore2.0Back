package com.example.myapp.service ;

import com.example.myapp.dao.AccessCart;
import com.example.myapp.data.Cart;
import com.example.myapp.utils.SessionUtils;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.* ; 
@Service
public class CartService implements com.example.myapp.service.Service{
    @Autowired
    AccessCart accessCart ;

    public boolean del(Object entity, HttpServletRequest request)
    {
        if(entity instanceof Integer)
        {
            int result = (int)entity ; 
            int user_id = SessionService.getUserId(request) ; 
            try{
                accessCart.removeCart(user_id, result);
            }
            catch(Exception err)
            {
                System.err.println(err);
                return false ; 
            }
            return true ; 
        }
        return false ; 
    }

    public boolean put(Object entity, HttpServletRequest request)
    {
        if(entity instanceof Integer)
        {
            int result = (int)entity ; 
            int user_id = SessionService.getUserId(request) ;
            try{
                accessCart.addCart(user_id , result) ;
            }
            catch(Exception err)
            {
                System.err.println(err);
                return false ; 
            }
            return true ; 

        }
        return false ; 
    }

    public Cart get(Object entity, HttpServletRequest request)
    {
        if(entity instanceof Cart)
        {
            Cart ret = (Cart)entity ; 
            return ret ; 
        }
        return null ; //false
    }

    public Cart[] getList(HttpServletRequest request)
    {
        int user_id = SessionService.getUserId(request) ;
        Cart[] ret ;
        try{
            ret = accessCart.getCartList(user_id) ;
        }
        catch(Exception err)
        {
            System.err.println(err);
            return new Cart[]{}; 
        }
        return ret ; 
    }
}