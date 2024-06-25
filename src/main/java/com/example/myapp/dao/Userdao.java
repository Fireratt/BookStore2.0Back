package com.example.myapp.dao; 

import org.springframework.stereotype.* ; 
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ; 
import com.example.myapp.BookList;
import com.example.myapp.data.Book;
import com.example.myapp.data.User;
public interface Userdao {

    boolean checkAdministrator(int id) ; 

    int banUser(int id) ; 

    List<User> getUserInfo() ; 

    int unbanUser(int id) ; 
    
    List<Map> getUserRank(String start , String end) ; 
}