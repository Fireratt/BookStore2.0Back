package com.example.myapp.daoimpl; 

import org.springframework.stereotype.* ; 
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ; 
import com.example.myapp.BookList;
import com.example.myapp.dao.Bookdao;
import com.example.myapp.data.Book;
import com.example.myapp.repository.AccessBook;
@Repository

public class Bookdaoimpl implements Bookdao{

    @Autowired
    private AccessBook accessBook ; 
    public Book[] findByPage(int page) 
    {
        return accessBook.findByPage(page) ; 
    }

    public Book findByBookId(int Book_Id)
    {
        return accessBook.findByBookId(Book_Id) ; 
    }

    public Book save(Book result)
    {
        return accessBook.save(result) ; 
    }

    public Book[] SearchByName(String name)
    {
        return accessBook.SearchByName(name) ; 
    }

    public int modifyBook(int book_id , String name , String author , int Storage) 
    {
        return accessBook.modifyBook(book_id, name, author, Storage) ; 
    }
    
    public int modifyCover(int book_id , String cover)
    {
        return accessBook.modifyCover(book_id, cover) ; 
    }

    public int addBook(String name ,double price ,String author, String description , int storage , String isbn , String cover) 
    {
        return accessBook.addBook(name, price, author, description, storage, isbn, cover) ; 
    }

    public int deleteBook(int book_id)
    {
        return accessBook.deleteBook(book_id) ; 
    }

    public Integer checkStorage(int book_id , int number)
    {
        return accessBook.checkStorage(book_id , number) ; 
    }

    public int updateStorage(int book_id , int number)
    {
        return accessBook.updateStorage(book_id, number) ; 
    }
}   