package com.example.myapp.daoimpl; 

import org.springframework.stereotype.* ;

import java.lang.reflect.Array;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.example.myapp.BookList;
import com.example.myapp.repository.AccessOrder;
import com.example.myapp.dao.*;
import com.example.myapp.data.*;
import org.springframework.dao.DataAccessException;
@Repository

public class Orderdaoimpl implements Orderdao{

    @Autowired
    private AccessOrder accessOrder ; 
    public Order[] getOrderList(int user_id) 
    {
        return accessOrder.getOrderList(user_id) ; 
    }
    
    public void save(int userId , String date)
    {
        accessOrder.save(userId, date);
    }

    public String[] getOrderItem(int order_id)
    {
        return accessOrder.getOrderItem(order_id) ; 
    }

    public int getNewOrderId() 
    {
        return accessOrder.getNewOrderId() ; 
    }

    public int saveOrderItem(int order_id , int book_id , int amount , int price) 
    {
        return accessOrder.saveOrderItem(order_id, book_id, amount, price) ; 
    }

    public Order[] searchOrder(int user_id , String query)
    {
        return accessOrder.searchOrder(user_id, query) ; 
    }

    public Order[] selectOrderByDate(int user_id , String start , String end)
    {
        return accessOrder.selectOrderByDate(user_id, start, end) ; 
    }
}