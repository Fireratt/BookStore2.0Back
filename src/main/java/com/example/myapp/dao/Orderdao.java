package com.example.myapp.dao; 

import org.springframework.stereotype.* ;

import java.lang.reflect.Array;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.example.myapp.BookList;
import com.example.myapp.data.*;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface Orderdao {
    public Order[] getOrderList(int user_id) ; 
    
    public void save(int userId , String date) ; 

    public String[] getOrderItem(int order_id) ; 

    int getNewOrderId() ;

    int saveOrderItem(int order_id , int book_id , int amount , int price) ;  

    Order[] searchOrder(int user_id , String query) ; 

    Order[] selectOrderByDate(int user_id , String start , String end) ; 

    public Page<Order> getAllOrder(Pageable pageStatus) ; 

    public Page<Order> selectAllOrderByDate(String start , String end , Pageable pageStatus) ; 
    
    public Page<Order> searchAllOrder(String query , Pageable pageStatus) ; 
}