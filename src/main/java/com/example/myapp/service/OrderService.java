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
import java.util.ArrayList;
@Service
public class OrderService implements com.example.myapp.service.Service
{
    @Autowired
    AccessOrder accessOrder;
    @Autowired
    AccessCart accessCart ; 
    public Order[] getList(HttpServletRequest request)
    {
        int user_id = SessionService.getUserId(request) ;
        Order[] ret ;
        try{
            ret = accessOrder.getOrderList(user_id) ;
            int length = ret.length ;
            for(int i = 0 ; i < length ; i++ )
            {
                String[] items = accessOrder.getOrderItem(ret[i].getOrderId()) ;
                int itemNumber = items.length ;  
                for(int j = 0 ; j < itemNumber ; j++)
                {
                    OrderItem single = new OrderItem(items[j]) ; 
                    ret[i].insertOrderItem(single);
                }
                // ret[i].setOrderItems(OrderItems);
            }
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
            result.setUserId(user_id);
            accessOrder.save(user_id,result.getDate()) ;
            int orderId = accessOrder.getNewOrderId() ; 
            ArrayList<OrderItem> items = result.getOrderItems() ; 

            int itemNum = items.size() ; 
            for(int i = 0 ; i < itemNum ; i++)
            {
                accessOrder.saveOrderItem(orderId , items.get(i).getBook_id() , items.get(i).getAmount()) ;  
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

