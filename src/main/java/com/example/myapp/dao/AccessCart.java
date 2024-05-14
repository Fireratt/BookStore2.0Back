package com.example.myapp.dao; 

import org.springframework.stereotype.* ; 
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ; 
import com.example.myapp.BookList;
import com.example.myapp.data.Cart;
import org.springframework.dao.DataAccessException; 
import javax.annotation.Resource;
import javax.sql.RowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet ; 
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration ;

import com.example.myapp.data.Book;
@Repository
public class AccessCart{
    @Autowired
    private JdbcTemplate jdbcTemplate ; 
    @Autowired
    private AccessBook accessBook ; 
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
    
    public Cart[] getCartList(int userId)
    {
        String sql = "select book_id from cart where user_id = ? ;" ; 
        SqlRowSet result = jdbcTemplate.queryForRowSet( sql, new Object[]
        {
            userId
        }) ; 
        result.last() ; 
        int size = result.getRow(); 
        result.beforeFirst() ; 
        int i = 0 ; 
        Cart[] cartList = new Cart[size] ; 
        while(result.next())
        {
            int book_id = result.getInt("book_id") ; 
            Book bookInfo= accessBook.getBookInfo(book_id) ; 
            System.out.println(bookInfo.Name) ; 
            Cart resultUnit = new Cart(userId , book_id,bookInfo.Price , bookInfo.Name) ; 
            cartList[i] = resultUnit ; 
            i = i + 1 ;
        }
        return cartList ; 
    }
    @Autowired
    public AccessCart(JdbcTemplate jdbc)
    {
        this.jdbcTemplate = jdbc;
    }
}