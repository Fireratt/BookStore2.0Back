package com.example.myapp.repository; 

import org.springframework.stereotype.* ; 
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ; 
import com.example.myapp.BookList;
import com.example.myapp.data.Cart;

import jakarta.persistence.IdClass;
import jakarta.transaction.Transactional;

import org.springframework.dao.DataAccessException; 
import javax.annotation.Resource;
import javax.sql.RowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet ; 
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.myapp.data.Book;
// @Repository
// public class AccessCart extends JpaRepository<Cart , Integer>{
//     @Autowired
//     private JdbcTemplate jdbcTemplate ; 
//     @Autowired
//     private AccessBook accessBook ; 
//     public void addCart(int userId,int bookId )
//     {
//         String sql = "insert ignore into cart values(?,?)" ; 
//         jdbcTemplate.update(sql
//         ,new Object[]{
//             userId,
//             bookId
//         }) ; 
//     }

//     public void removeCart(int userId,int bookId )
//     {
//         String sql = "delete from cart where user_id=" + userId + " and " +"book_id=" + bookId ;  
//         jdbcTemplate.update(sql) ; 
//     }
    
//     public Cart[] getCartList(int userId)
//     {
//         String sql = "select book_id from cart where user_id = ? ;" ; 
//         SqlRowSet result = jdbcTemplate.queryForRowSet( sql, new Object[]
//         {
//             userId
//         }) ; 
//         result.last() ; 
//         int size = result.getRow(); 
//         result.beforeFirst() ; 
//         int i = 0 ; 
//         Cart[] cartList = new Cart[size] ; 
//         while(result.next())
//         {
//             int book_id = result.getInt("book_id") ; 
//             Book bookInfo= accessBook.getBookInfo(book_id) ; 
//             System.out.println(bookInfo.Name) ; 
//             Cart resultUnit = new Cart(userId , book_id,bookInfo.Price , bookInfo.Name) ; 
//             cartList[i] = resultUnit ; 
//             i = i + 1 ;
//         }
//         return cartList ; 
//     }
//     @Autowired
//     public AccessCart(JdbcTemplate jdbc)
//     {
//         this.jdbcTemplate = jdbc;
//     }
// }

public interface AccessCart extends JpaRepository<Cart,Integer>{

    // @Query(value="select a.user_id , a.book_id , b.price , b.name from cart a join book b on a.book_id = b.book_id where user_id = ?1 ", nativeQuery = true)
    @Query(value="select c from Cart c where c.userId = ?1 and c.book.valid = 1", nativeQuery = false)
    Cart[] findByUserId(int user_id) ; 

    @Modifying
    @Transactional
    @Query(value = "delete from cart c where c.user_id= ?1 and c.book_id = ?2", nativeQuery = true )
    void deleteByIds(int userId , int bookId) ; 
    
    // Cart findBy
    @Modifying
    @Transactional
    @Query(value= "insert into Cart(userId , bookId) values(?1,?2)" , nativeQuery = false)
    void save(int userId,int bookId) ; 
}