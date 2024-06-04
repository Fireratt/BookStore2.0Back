package com.example.myapp.repository; 

import org.springframework.stereotype.* ; 
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ; 
import com.example.myapp.BookList;
import com.example.myapp.data.Book;

import jakarta.transaction.Transactional;

import org.springframework.dao.DataAccessException; 
import javax.annotation.Resource;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration ;
import java.sql.ResultSet;
import org.springframework.jdbc.support.rowset.SqlRowSet ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
// @Repository
// public class AccessBook {
//     @Autowired
//     private JdbcTemplate jdbcTemplate ; 

//     // once the getBookList can return ; it is used as the book's number of one page as well 
//     static final int MAXBOOKNUM = 30 ; 
//     public void addBook(Book book)
//     {
//         try{
//             jdbcTemplate.update("insert ignore into book values(?,?,?,?,?,?,?)" , 
//                 new Object[]{
//                     book.Book_Id , 
//                     book.Author ,
//                     book.Name , 
//                     book.Price , 
//                     book.Description , 
//                     book.Storage , 
//                     book.Real_Price 
//                 }
//             ) ; 
//         }
//         catch(DataAccessException err)
//         {
//             System.out.println("JDBCERROR::" + err.toString()) ; 
//         }
//     }
//     // just return the bookInfo's key 
//     public Book getBookInfo(int bookId)
//     {
//         String sql = "select * from book where book_id = ?" ;
//         Map<String,Object> temMap = jdbcTemplate.queryForMap(sql , 
//         new Object[]
//         {
//             bookId
//         }) ; 
//         Book ret = new Book(Integer.parseInt(temMap.get("Book_Id").toString())
//         ,temMap.get("Name").toString()
//         ,temMap.get("Author").toString()
//         ,temMap.get("Description").toString()
//          , Double.parseDouble( temMap.get("Price").toString())
//          ,Double.parseDouble(temMap.get("Real_Price").toString())
//          ,Integer.parseInt( temMap.get("Storage").toString())) ; 
//         return ret ; 
//     }

//     public Book_Basic[] getBookList(int pageNum)
//     {
//         // check the session 

//         // page start from 1 
//         String sql = "select * from book where book_id >=" +  (pageNum-1) * MAXBOOKNUM ; 
//         SqlRowSet rs = jdbcTemplate.queryForRowSet(sql) ; 
        
//         Book_Basic[] results = new Book_Basic[MAXBOOKNUM];
//         int cnt = 0 ; 
//         while(rs.next() && cnt < MAXBOOKNUM)
//         {
//             String id = rs.getString("book_id") ; 
//             String name = rs.getString("name") ; 
//             Double price = rs.getDouble("price") ;
//             results[cnt] = new Book_Basic(Integer.parseInt(id),name,price) ;  
//             cnt = cnt + 1 ; 
//         }
//         // dont return the null 
//         Book_Basic[] result = new Book_Basic[cnt];
//         for(int i = 0 ; i < cnt ; i++)
//         {
//             result[i] = results[i] ; 
//         }
//         return result ; 
//     }

//     @Autowired
//     public AccessBook(JdbcTemplate jdbc)
//     {
//         this.jdbcTemplate = jdbc;
//     }
// }
public interface AccessBook extends JpaRepository<Book , Integer>{

    @Query(value = "select b from Book b where b.valid=1")
    Page<Book> findByPage(Pageable pageStatus) ; 

    Book findByBookId(int Book_Id) ; 
    @Modifying
    Book save(Book result) ; 

    @Query(value = "select b from Book b where b.Name like %?1% and b.valid=1")
    Book[] SearchByName(String name) ;

    @Modifying
    @Transactional
    @Query(value = "update Book b set b.Name=?2,b.Author=?3,b.Storage=?4 where b.bookId=?1")
    int modifyBook(int book_id , String name , String author , int Storage) ;
    
    @Modifying
    @Transactional
    @Query(value = "update Book b set b.cover=?2 where b.bookId=?1") 
    int modifyCover(int book_id , String cover) ; 

    @Modifying
    @Query(value = "insert into Book(Name,Price ,Author, Description ,Storage , isbn , cover) values(?1,?2,?3,?4,?5,?6,?7)")
    @Transactional
    int addBook(String name ,double price ,String author, String description , int storage , String isbn , String cover) ; 

    @Modifying
    @Transactional
    @Query(value = "update Book b set b.valid=0 where b.bookId=?1")
    int deleteBook(int book_id) ; 

    @Query(value = "select b.bookId from Book b where b.bookId = ?1 and b.Storage > ?2")
    Integer checkStorage(int book_id , int number) ; 

    @Modifying
    @Transactional
    @Query(value = "update Book b set b.Storage=b.Storage-?2 where b.bookId=?1")
    int updateStorage(int book_id , int number) ; 
}   