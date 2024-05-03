package com.example.myapp.dao; 

import org.springframework.stereotype.* ; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ; 
import com.example.myapp.BookList;
import com.example.myapp.data.Book;
import org.springframework.dao.DataAccessException; 
import javax.annotation.Resource;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration ;
@Repository
public class AccessBook {
    @Autowired
    private JdbcTemplate jdbcTemplate ; 

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
    @Autowired
    public AccessBook(JdbcTemplate jdbc)
    {
        this.jdbcTemplate = jdbc;
    }
}