package com.example.myapp.service ;

import com.example.myapp.dao.AccessCart;
import com.example.myapp.data.Cart;
import com.example.myapp.dto.Cart_dto;
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
                accessCart.deleteByIds(user_id, result);
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
                accessCart.save(user_id , result) ;
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

    public Cart_dto[] getList(HttpServletRequest request)
    {
        int user_id = SessionService.getUserId(request) ;
        // try{
            Cart[] result = accessCart.findByUserId(user_id) ; 
            Cart_dto[] ret = new Cart_dto[result.length] ; 
            
            int cnt = 0 ; 
            for (Cart cart : result) {
                ret[cnt] = new Cart_dto(user_id, cart.getBookId(), cart.getBook().getPrice(),cart.getBook().getName()) ; 
                cnt ++ ; 
            }
            return ret ; 
        // }
        // catch(Exception err)
        // {
        //     System.err.println(err);
        //     return new Cart_dto[]{}; 
        // }
    }
}