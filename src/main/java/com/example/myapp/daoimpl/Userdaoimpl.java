package com.example.myapp.daoimpl; 

import org.springframework.stereotype.* ; 
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ; 
import com.example.myapp.BookList;
import com.example.myapp.dao.Userdao;
import com.example.myapp.data.Book;
import com.example.myapp.repository.AccessUser;
@Repository

public class Userdaoimpl implements Userdao{

    @Autowired
    AccessUser accessUser ; 
    public boolean checkAdministrator(int id) 
    {
        return accessUser.checkAdministrator(id) ; 
    }
}