package com.example.myapp.daoimpl; 

import org.springframework.stereotype.* ;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.example.myapp.BookList;
import com.example.myapp.repository.AccessOrder;
import org.springframework.data.domain.Pageable;
import com.example.myapp.dao.*;
import com.example.myapp.data.*;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
@Repository

public class Orderdaoimpl implements Orderdao{

    @Autowired
    private AccessOrder accessOrder ; 
    public Order[] getOrderList(int user_id) 
    {
        return accessOrder.getOrderList(user_id) ; 
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Order order) 
    {
        accessOrder.save(order);
    }
    public String[] getOrderItem(int order_id)
    {
        return accessOrder.getOrderItem(order_id) ; 
    }

    public int getNewOrderId() 
    {
        return accessOrder.getNewOrderId() ; 
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public int saveOrderItem(int order_id , int book_id , int amount , BigDecimal price) 
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

    public Page<Order> getAllOrder(Pageable pageStatus)
    {
        return accessOrder.getAllOrder(pageStatus) ; 
    }

    public Page<Order> searchAllOrder(String query , Pageable pageStatus)
    {
        return accessOrder.searchAllOrder(query , pageStatus) ; 
    }
    public Page<Order> selectAllOrderByDate(String start , String end , Pageable pageStatus)
    {
        return accessOrder.selectAllOrderByDate(start, end , pageStatus) ; 
    }

}