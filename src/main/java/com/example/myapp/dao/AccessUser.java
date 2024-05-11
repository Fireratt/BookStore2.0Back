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
import com.example.myapp.data.User; 
@Repository
public class AccessUser{
    @Autowired
    private JdbcTemplate jdbcTemplate ; 
    
    public User getUser(String userId)
    {
        String sql = "select * from user which userId = " + userId ; 
        Map<String,Object> response =  jdbcTemplate.queryForMap(sql) ; 
        return new User(response) ; 
    }
    public void setUserData(User newUserInfo)
    {
        String sql = "replace into user values(?,?,?,?,?) " ;
        Map<String ,String> data = newUserInfo.getMap() ; 
        jdbcTemplate.update(sql,
        new Object[]{
            data.get("id") , 
            data.get("username") , 
            data.get("date") , 
            data.get("phone"),
            data.get("mail") ,
        }) ; 
    }

    @Autowired
    public AccessUser(JdbcTemplate jdbc)
    {
        this.jdbcTemplate = jdbc;
    }
}