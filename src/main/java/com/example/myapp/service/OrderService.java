package com.example.myapp.service ;

import com.example.myapp.data.Cart;
import com.example.myapp.utils.SessionUtils;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.* ; 
import com.example.myapp.data.OrderItem;
import com.example.myapp.dto.OrderStatistic_dto;
import com.example.myapp.dto.Order_dto;
import com.example.myapp.service.OrderService.StorageNotEnoughException;

import java.util.*;
@Service
public interface OrderService {
    public Order_dto[] getList(HttpServletRequest request) ;

    public boolean put(Object entity , HttpServletRequest request) throws StorageNotEnoughException;

    public boolean del(Object entity , HttpServletRequest request) ;

    public Object get(Object entity , HttpServletRequest request) ;

    public Order_dto[] searchOrder(String query ,  HttpServletRequest request) ; 

    public Order_dto[] selectOrderByDate(String start , String end ,  HttpServletRequest request) ; 

    public OrderStatistic_dto countOrder(String start , String end , HttpServletRequest request) ; 

    public class StorageNotEnoughException extends Exception
    {
        public static final String message = "This book is not enough for this order , Or the book have been deleted" ;
        public int book_id ; 
        public StorageNotEnoughException(int book_id)
        {
            this.book_id = book_id ; 
        } 
    }
}

