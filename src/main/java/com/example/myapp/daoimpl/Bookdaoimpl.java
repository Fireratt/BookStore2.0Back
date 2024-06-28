package com.example.myapp.daoimpl; 

import org.springframework.stereotype.* ; 
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate ; 
import com.example.myapp.BookList;
import com.example.myapp.dao.Bookdao;
import com.example.myapp.data.Book;
import com.example.myapp.dto.BookRank;
import com.example.myapp.repository.AccessBook;
@Repository

public class Bookdaoimpl implements Bookdao{

    @Autowired
    private AccessBook accessBook ; 
    public Page<Book> findByPage(Pageable pageStatus) 
    {
        return accessBook.findByPage(pageStatus) ; 
    }

    public Book findByBookId(int Book_Id)
    {
        return accessBook.findByBookId(Book_Id) ; 
    }

    public Book save(Book result)
    {
        return accessBook.save(result) ; 
    }

    public Page<Book> SearchByName(String name, Pageable pageStatus)
    {
        return accessBook.SearchByName(name , pageStatus) ; 
    }

    public int modifyBook(int book_id , String name , String author , int Storage ,  String isbn , double price ) 
    {
        return accessBook.modifyBook(book_id, name, author, Storage , isbn , price) ; 
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

    public List<Map> getBookRank(String start , String end,Pageable pageStatus)
    {
        return accessBook.getBookRank(start , end ) ; 
    }

}