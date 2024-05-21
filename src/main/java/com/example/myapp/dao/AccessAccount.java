package com.example.myapp.dao; 

import org.springframework.stereotype.* ;

import com.example.myapp.data.UserAuth;

// import java.io.Console;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ; 
// import com.example.myapp.BookList;
// import com.example.myapp.data.Book;
// import org.springframework.dao.DataAccessException; 
import org.springframework.dao.IncorrectResultSizeDataAccessException ; 

// import javax.annotation.Resource;
// import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration ;
// import java.sql.*;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.myapp.data.User;
// @Repository
// public class AccessAccount {
//     @Autowired
//     private JdbcTemplate jdbcTemplate ; 
//     @Autowired
//     private Common common ; 

//     public String confirmLogin(String userName , String password)
//     {
//         String sql = "Select user_id from user where name = ?" ;
//         String sqlForAuth =  "Select user_id from userauth where user_id = ? and pwd = ?" ;
//         Map<String,Object> result ;
//         try{

//             result = this.jdbcTemplate.queryForMap(sql , new Object[]
//             {
//                 userName , 
//             }) ;
//             String user_id = result.get("user_id").toString() ; 
            
//             result = this.jdbcTemplate.queryForMap(sqlForAuth , 
//             new Object[]
//             {
//                 user_id , password 
//             }) ; 
//             return user_id ; 
//         }
//         catch(IncorrectResultSizeDataAccessException err)
//         {
//             System.out.println("Password or userName Error") ; 
//             return ""; 
//         }
        
//     }

//     public void register(String userName , String password , String mail)
//     {
//         String sql = "Insert Into user Values(?,?,?)" ; 
//         String sqlPWD = "Insert Into userauth Values(?,?)" ; 
//         String max = common.getMax("user", "user_id") ; 
//         int maxNum = Integer.parseInt(max) ; 
//         // increase the key 
//         maxNum = maxNum + 1 ; 
//         this.jdbcTemplate.update(
//             sql , new Object[]
//             {
//                 maxNum,userName , mail 
//             }
//         ) ; 
//         this.jdbcTemplate.update(
//             sql , new Object[]
//             {
//                 userName , password 
//             }
//         ) ; 
//     }
    
//     @Autowired
//     public AccessAccount(JdbcTemplate jdbc)
//     {
//         this.jdbcTemplate = jdbc;
//     }
// }
public interface AccessAccount extends JpaRepository<UserAuth , Integer>{

    @Query(value = "")
    public String confirmLogin(String userName , String password) ; 
}