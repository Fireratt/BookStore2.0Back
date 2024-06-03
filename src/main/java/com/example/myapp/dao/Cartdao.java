package com.example.myapp.dao; 

import org.springframework.stereotype.* ; 
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ; 
import com.example.myapp.BookList;
import com.example.myapp.data.Cart;

public interface Cartdao {

    // @Query(value="select a.user_id , a.book_id , b.price , b.name from cart a join book b on a.book_id = b.book_id where user_id = ?1 ", nativeQuery = true)
    Cart[] findByUserId(int user_id) ; 

    void deleteByIds(int userId , int bookId) ; 
    
    // Cart findBy

    void save(int userId,int bookId) ; 
}