package com.example.myapp.dao; 

import org.springframework.stereotype.* ; 
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ; 
import com.example.myapp.BookList;
import com.example.myapp.data.Book;
import org.springframework.dao.DataAccessException; 
import javax.annotation.Resource;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration ;
import com.example.myapp.data.Book_Basic;
import java.sql.ResultSet;
import org.springframework.jdbc.support.rowset.SqlRowSet ; 
@Repository
public class AccessBook {
    @Autowired
    private JdbcTemplate jdbcTemplate ; 

    // once the getBookList can return ; it is used as the book's number of one page as well 
    static final int MAXBOOKNUM = 30 ; 
    public void addBook(Book book)
    {
        try{
            jdbcTemplate.update("insert ignore into book values(?,?,?,?,?,?,?)" , 
                new Object[]{
                    book.Book_Id , 
                    book.Author ,
                    book.Name , 
                    book.Price , 
                    book.Description , 
                    book.Storage , 
                    book.Real_Price 
                }
            ) ; 
        }
        catch(DataAccessException err)
        {
            System.out.println("JDBCERROR::" + err.toString()) ; 
        }
    }
    // just return the bookInfo's key 
    public Book getBookInfo(int bookId)
    {
        String sql = "select * from book where book_id = " + bookId ; 
        Map<String,Object> temMap = jdbcTemplate.queryForMap(sql) ; 
        Book ret = new Book(Integer.parseInt(temMap.get("Book_Id").toString())
        ,temMap.get("Name").toString()
        ,temMap.get("Author").toString()
        ,temMap.get("Description").toString()
         , Double.parseDouble( temMap.get("Price").toString())
         ,Double.parseDouble(temMap.get("Real_Price").toString())
         ,Integer.parseInt( temMap.get("Storage").toString())) ; 
        return ret ; 
    }

    public Book_Basic[] getBookList(int pageNum)
    {
        // page start from 1 
        String sql = "select * from book where book_id >=" +  (pageNum-1) * MAXBOOKNUM ; 
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql) ; 
        Book_Basic[] ret = new Book_Basic[MAXBOOKNUM];
        int cnt = 0 ; 
        while(rs.next() && cnt < MAXBOOKNUM)
        {
            String id = rs.getString("book_id") ; 
            String name = rs.getString("name") ; 
            Double price = rs.getDouble("price") ;
            ret[cnt] = new Book_Basic(Integer.parseInt(id),name,price) ;  
            cnt = cnt + 1 ; 
        }

        return ret ; 
    }

    @Autowired
    public AccessBook(JdbcTemplate jdbc)
    {
        this.jdbcTemplate = jdbc;
    }
}