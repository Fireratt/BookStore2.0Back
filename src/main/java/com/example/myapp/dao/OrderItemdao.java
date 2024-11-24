package com.example.myapp.dao; 

import org.springframework.stereotype.* ;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.example.myapp.BookList;
import com.example.myapp.data.*;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface OrderItemdao {
    public int saveOrderItem(int order_id , int book_id , int amount , BigDecimal price) ;
}