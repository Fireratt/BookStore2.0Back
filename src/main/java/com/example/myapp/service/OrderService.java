package com.example.myapp.service ;

import com.example.myapp.dao.AccessCart;
import com.example.myapp.dao.AccessOrder;
import com.example.myapp.data.Cart;
import com.example.myapp.utils.SessionUtils;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.myapp.data.Order;
import jakarta.servlet.http.* ; 
@Service
public class OrderService implements com.example.myapp.service.Service
{
    @Autowired
    AccessOrder accessOrder;

    public Order[] getList(HttpServletRequest request)
    {
        int user_id = SessionService.getUserId(request) ;
        Order[] ret ;
        try{
            ret = accessOrder.getOrderList(user_id) ;
        }
        catch(Exception err)
        {
            System.err.println(err);
            return new Order[]{}; 
        }
        return ret ; 
    }

    public boolean put(Object entity , HttpServletRequest request) 
    {
        if(entity instanceof Order)
        {
            Order result = (Order)entity ; 
            int user_id = SessionService.getUserId(request) ;
            accessOrder.addOrder(user_id, result) ;
            return true ; 
        }
        return false ; 
    }

    public boolean del(Object entity , HttpServletRequest request)
    {
        if(entity instanceof Order)
        {
            Order result = (Order)entity ; 
            int user_id = SessionService.getUserId(request) ;
            accessOrder.deleteOrder(user_id, result) ;
            return true ; 
        }
        return false ; 
    }

    public Object get(Object entity , HttpServletRequest request)
    {
        return entity ; 
    }

}

