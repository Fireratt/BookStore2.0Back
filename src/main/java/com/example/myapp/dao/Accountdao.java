package com.example.myapp.dao; 

import org.springframework.stereotype.* ;

import com.example.myapp.data.UserAuth;

import java.sql.SQLIntegrityConstraintViolationException;
// import java.io.Console;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ;
import org.springframework.dao.DataIntegrityViolationException;
// import com.example.myapp.BookList;
// import com.example.myapp.data.Book;
// import org.springframework.dao.DataAccessException; 
import org.springframework.dao.IncorrectResultSizeDataAccessException ; 

public interface Accountdao{

    public List<Map> confirmLogin(String userName , String password) ; 
    
    public void register(String username, String mail) throws DataIntegrityViolationException ; 

    int getNewUserId() ;

    public void saveAuth(int user_id , String pwd) throws DataIntegrityViolationException; 
}