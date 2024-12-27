package com.example.myapp.repository; 

import org.springframework.stereotype.* ;

import java.io.Console;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ; 
import org.springframework.jdbc.support.rowset.SqlRowSet ; 
import com.example.myapp.BookList;
import com.example.myapp.data.Book;
import org.springframework.dao.DataAccessException; 
import org.springframework.dao.IncorrectResultSizeDataAccessException ; 

import javax.annotation.Resource;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration ;
import java.sql.*;
@Repository
public class Common {
    @Autowired
    private JdbcTemplate jdbcTemplate ; 
    // the the max value for a column (generously it will be a id , server should assign a increment id to it)
    public String getMax(String tableName , String columnName)
    {
        String sql = "SELECT MAX(?) AS ordered From ?" ;
        SqlRowSet result = this.jdbcTemplate.queryForRowSet(sql , 
        new Object[]
        {
            columnName , tableName 
        })  ; 
        String max = result.getString(1) ; 
        return max ; 
    }

    @Autowired
    public Common(JdbcTemplate jdbc)
    {
        this.jdbcTemplate = jdbc;
    }
}