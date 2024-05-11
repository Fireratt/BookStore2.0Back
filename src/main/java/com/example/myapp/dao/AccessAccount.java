package com.example.myapp.dao; 

import org.springframework.stereotype.* ;

import java.io.Console;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ; 
import com.example.myapp.BookList;
import com.example.myapp.data.Book;
import org.springframework.dao.DataAccessException; 
import org.springframework.dao.IncorrectResultSizeDataAccessException ; 

import javax.annotation.Resource;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration ;
@Repository
public class AccessAccount {
    @Autowired
    private JdbcTemplate jdbcTemplate ; 

    public void confirmLogin(String userName , String password)
    {
        String sql = "Select username from account which username=" + userName + "and password=" + password ; 
        try{
        this.jdbcTemplate.queryForMap(sql) ;
        }
        catch(IncorrectResultSizeDataAccessException err)
        {
            System.out.println("Password Error") ; 
        }
    }
    
    
    @Autowired
    public AccessAccount(JdbcTemplate jdbc)
    {
        this.jdbcTemplate = jdbc;
    }
}