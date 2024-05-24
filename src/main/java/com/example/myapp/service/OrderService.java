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
import com.example.myapp.data.OrderItem;
import com.example.myapp.dto.Order_dto;

import java.util.*;
@Service
public class OrderService implements com.example.myapp.service.Service
{
    @Autowired
    AccessOrder accessOrder;
    @Autowired
    AccessCart accessCart ; 
    public Order_dto[] getList(HttpServletRequest request)
    {
        int user_id = SessionService.getUserId(request) ;
        Order[] ret ;
        // try{
            ret = accessOrder.getOrderList(user_id) ;
            String result = ret[0].toString() ; 
            int length = ret.length ;
            int cnt = 1 ; 
            Order_dto[] orderList = new Order_dto[length] ; 
            System.out.println("Get orderList end,result :" + result);

            for (int i = 0 ; i < length ; i++) {
                // orderList[cnt] = order.toDto() ;  
                Order ret2 = ret[i] ; 
                orderList[i] = ret2.toDto() ;
            }
            return orderList ; 
        // }
        // catch(Exception err)
        // {
        //     StackTraceElement stackInfo = err.getStackTrace()[0] ; 
        //     System.err.println(err + stackInfo.getClassName() + ":" 
        //     + stackInfo.getMethodName() + ":" + stackInfo.getLineNumber());
        //     return new Order_dto[]{} ;  
        // }
        // return new Order_dto[]{} ;  

    }
    public boolean put(Object entity , HttpServletRequest request) 
    {
        if(entity instanceof Order)
        {
            Order result = (Order)entity ; 
            int user_id = SessionService.getUserId(request) ;
            result.setUserId(user_id);
            accessOrder.save(user_id,result.getDate()) ;
            int orderId = accessOrder.getNewOrderId() ; 
            List<OrderItem> items = result.getOrderItems() ; 

            int itemNum = items.size() ; 
            for(int i = 0 ; i < itemNum ; i++)
            {
                accessOrder.saveOrderItem(orderId , items.get(i).getBook_id() , items.get(i).getAmount() ,(int)items.get(i).getPrice()) ;  
                accessCart.deleteByIds(user_id, items.get(i).getBook_id()) ; 
            }
        
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
            // accessOrder.deleteOrder(user_id, result) ;
            return true ; 
        }
        return false ; 
    }

    public Object get(Object entity , HttpServletRequest request)
    {
        return entity ; 
    }

}

