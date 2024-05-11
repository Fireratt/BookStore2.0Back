package com.example.myapp.dao; 

import org.springframework.stereotype.* ; 
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ; 
import com.example.myapp.BookList;
import com.example.myapp.data.Book;
import org.springframework.dao.DataAccessException; 
import javax.annotation.Resource;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration ;
@Repository
public class AccessOrder{
    @Autowired
    private JdbcTemplate jdbcTemplate ; 
    
    @Autowired
    public AccessOrder(JdbcTemplate jdbc)
    {
        this.jdbcTemplate = jdbc;
    }
}