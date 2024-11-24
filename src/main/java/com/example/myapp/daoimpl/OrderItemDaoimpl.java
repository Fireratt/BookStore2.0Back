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
public class OrderItemDaoimpl implements OrderItemdao{
    @Autowired
    private AccessOrder accessOrder ; 
    @Transactional(propagation = Propagation.REQUIRED)
    public int saveOrderItem(int order_id , int book_id , int amount , BigDecimal price) 
    {
        // int error = 10 / 0 ;    // trigger error ;
        return accessOrder.saveOrderItem(order_id, book_id, amount, price) ; 
    }
}