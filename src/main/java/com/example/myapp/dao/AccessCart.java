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
public class AccessCart{
    @Autowired
    private JdbcTemplate jdbcTemplate ; 

    public void addCart(int userId,int bookId )
    {
        String sql = "insert ignore into cart values(?,?)" ; 
        jdbcTemplate.update(sql
        ,new Object[]{
            userId,
            bookId
        }) ; 
    }

    public void removeCart(int userId,int bookId )
    {
        String sql = "delete from cart where userId=" + userId + " and " +"bookId=" + bookId ;  
        jdbcTemplate.update(sql) ; 
    }
    
    @Autowired
    public AccessCart(JdbcTemplate jdbc)
    {
        this.jdbcTemplate = jdbc;
    }
}