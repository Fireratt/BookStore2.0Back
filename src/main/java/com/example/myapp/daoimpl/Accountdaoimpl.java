package com.example.myapp.daoimpl; 

import org.springframework.stereotype.* ;

import com.example.myapp.dao.Accountdao;
import com.example.myapp.data.UserAuth;

import java.sql.SQLIntegrityConstraintViolationException;
// import java.io.Console;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ;
import org.springframework.dao.DataIntegrityViolationException;
import com.example.myapp.repository.AccessAccount;
// import com.example.myapp.BookList;
// import com.example.myapp.data.Book;
// import org.springframework.dao.DataAccessException; 
import org.springframework.dao.IncorrectResultSizeDataAccessException ; 
@Repository
public class Accountdaoimpl implements Accountdao{
    @Autowired
    private AccessAccount accessAccount ; 
    public List<Map> confirmLogin(String userName , String password)
    {
        return accessAccount.confirmLogin(userName , password) ; 
    }
    
    public void register(String username, String mail) throws DataIntegrityViolationException
    {
        accessAccount.register(username , mail) ; 
    }

    public int getNewUserId() 
    {
        return accessAccount.getNewUserId() ; 
    }

    public void saveAuth(int user_id , String pwd) throws DataIntegrityViolationException
    {
        accessAccount.saveAuth(user_id, pwd);
    }
}