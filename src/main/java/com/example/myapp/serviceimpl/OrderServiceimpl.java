package com.example.myapp.serviceimpl ;

import com.example.myapp.dao.Cartdao;
import com.example.myapp.dao.OrderItemdao;
import com.example.myapp.dao.Bookdao;
import com.example.myapp.dao.Orderdao;
import com.example.myapp.daoimpl.OrderItemDaoimpl;
import com.example.myapp.dto.OrderStatistic_dto;
import com.example.myapp.data.Cart;
import com.example.myapp.utils.SessionUtils;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.myapp.data.Order;
import jakarta.servlet.http.* ; 
import com.example.myapp.data.OrderItem;
import com.example.myapp.dto.Order_dto;
import com.example.myapp.service.OrderService;
import com.example.myapp.service.SessionService;
import java.util.*;
@Service
public class OrderServiceimpl implements OrderService
{
    @Autowired
    Orderdao accessOrder;
    @Autowired
    Cartdao accessCart ; 
    @Autowired
    Bookdao accessBook ; 
    @Autowired
    OrderItemdao accessOrderItem ; 
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
    @Transactional
    public boolean put(Object entity , int user_id) throws StorageNotEnoughException
    {
        if(entity instanceof Order)
        {
            Order result = (Order)entity ; 
            result.setUserId(user_id);
            // should first check the storage is enough
            List<OrderItem> items = result.getOrderItems() ; 
                for(int i = 0 ; i < items.size() ; i++)
                {
                    int book_id = items.get(i).getBook_id() ; 
                    if(accessBook.checkStorage(book_id,  items.get(i).getAmount()) == null)
                    {
                        throw new StorageNotEnoughException(book_id) ; 
                    }
                }
            Order mid = new Order() ; 
            mid.setDate(result.getDate());
            mid.setUserId(user_id);
            System.out.println(mid.getUserId());
            try
            {accessOrder.save(mid) ;}
            catch(Exception e){
                //IGNORE 
            }
            int id = mid.getOrderId() ; 
            System.out.println(id) ; 
            int orderId = id ; 

            int itemNum = items.size() ; 
            for(int i = 0 ; i < itemNum ; i++)
            {
                accessBook.updateStorage(items.get(i).getBook_id() , items.get(i).getAmount()) ; 
                accessOrderItem.saveOrderItem(orderId , items.get(i).getBook_id() , items.get(i).getAmount() ,items.get(i).getPrice()) ;  
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

    public Order_dto[] searchOrder(String query ,  HttpServletRequest request)
    {
        int user_id = SessionService.getUserId(request) ;
        Order[] result = accessOrder.searchOrder(user_id, query) ; 
        Order_dto[] ret = new Order_dto[result.length] ; 
        int cnt = 0 ; 
        for (Order order : result) {
            ret[cnt] = order.toDto() ; 
            cnt++; 
        }
        return ret ;
    }

    public Order_dto[] selectOrderByDate(String start , String end ,  HttpServletRequest request)
    {
        int user_id = SessionService.getUserId(request) ;
        Order[] result = accessOrder.selectOrderByDate(user_id, start, end) ; 
        Order_dto[] ret = new Order_dto[result.length] ; 
        int cnt = 0 ; 
        for (Order order : result) {
            ret[cnt] = order.toDto() ; 
            cnt++; 
        }
        return ret ;
    }

    public OrderStatistic_dto countOrder(String start , String end , HttpServletRequest request)
    {
        int user_id = SessionService.getUserId(request) ;
        Order[] result = accessOrder.selectOrderByDate(user_id, start, end) ; 
        OrderStatistic_dto ret = new OrderStatistic_dto(result) ; 
        return ret ; 
    }
}

