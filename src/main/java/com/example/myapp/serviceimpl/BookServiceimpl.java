package com.example.myapp.serviceimpl;
import com.example.myapp.dao.Bookdao;
import com.example.myapp.data.Book;
import com.example.myapp.data.Cart;
import com.example.myapp.dto.Book_Basic_dto;
import com.example.myapp.dto.Book_dto;
import com.example.myapp.service.BookService;
import com.example.myapp.utils.SessionUtils;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.myapp.service.SessionService;

import jakarta.servlet.http.* ; 
@Service
public class BookServiceimpl implements BookService{
    @Autowired
    Bookdao accessBook ; 
    final int PAGE_SIZE = 10 ; 
    public boolean del(Object entity, HttpServletRequest request)
    {
        if(entity instanceof Integer)
        {
            int result = (int)entity ; 
            int user_id = SessionService.getUserId(request) ; 
            try{
                accessBook.deleteBook(result);
            }
            catch(Exception err)
            {
                System.err.println(err);
                return false ; 
            }
            return true ; 
        }
        return false ; 
    }

    public boolean put(Object entity, HttpServletRequest request)
    {
        if(entity instanceof Book)
        {
            Book result = (Book)entity ; 
            int user_id = SessionService.getUserId(request) ;
            try{
                accessBook.save(result) ;
            }
            catch(Exception err)
            {
                System.err.println(err);
                return false ; 
            }
            return true ; 

        }
        return false ; 
    }

    public Book_dto get(Object entity, HttpServletRequest request)
    {
        if(entity instanceof Integer)
        {
            Book ret = accessBook.findByBookId((Integer)entity) ; 
            return ret.toDto() ; 
        }
        return null ; //false
    }

    public Book_Basic_dto[] getList(HttpServletRequest request)
    {
        int user_id = SessionService.getUserId(request) ;
        Book[] result ;
        Book_Basic_dto[] ret = null ; 
        try{
            result = accessBook.findByPage(1*PAGE_SIZE) ;
            ret = new Book_Basic_dto[result.length] ; 
            int cnt = 0 ; 
            for (Book book : result ) {
                ret[cnt] = book.toBasicDto() ; 
                cnt++ ; 
            }
        }
        catch(Exception err)
        {
            System.err.println(err);
            return new Book_Basic_dto[]{}; 
        }
        return ret ; 
    }

    public Book_Basic_dto[] searchBook(String query , HttpServletRequest request)
    {
        int user_id = SessionService.getUserId(request) ;
        Book[] result ;
        Book_Basic_dto[] ret = null ; 
        try{
            result = accessBook.SearchByName(query) ; 
            ret = new Book_Basic_dto[result.length] ; 
            int cnt = 0 ; 
            for (Book book : result ) {
                ret[cnt] = book.toBasicDto() ; 
                cnt++ ; 
            }
        }
        catch(Exception err)
        {
            System.err.println(err);
            return new Book_Basic_dto[]{}; 
        }
        return ret ; 
    }
}