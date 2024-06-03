package com.example.myapp.daoimpl; 

import org.springframework.stereotype.* ; 
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ; 
import com.example.myapp.BookList;
import com.example.myapp.dao.Cartdao;
import com.example.myapp.data.Cart;
import com.example.myapp.repository.AccessCart;
@Repository

public class Cartdaoimpl implements Cartdao{

    @Autowired
    AccessCart accessCart ; 
    // @Query(value="select a.user_id , a.book_id , b.price , b.name from cart a join book b on a.book_id = b.book_id where user_id = ?1 ", nativeQuery = true)
    public Cart[] findByUserId(int user_id)
    {
        return accessCart.findByUserId(user_id) ; 
    }

    public void deleteByIds(int userId , int bookId)
    {
        accessCart.deleteByIds(userId, bookId);
    }
    
    // Cart findBy

    public void save(int userId,int bookId)
    {
        accessCart.save(userId, bookId);
    }
}