package com.example.myapp.serviceimpl;
import com.example.myapp.dao.Bookdao;
import com.example.myapp.data.Book;
import com.example.myapp.data.Cart;
import com.example.myapp.dto.Book_Basic_dto;
import com.example.myapp.dto.Book_dto;
import com.example.myapp.service.BookService;
import com.example.myapp.utils.PageUtils;
import com.example.myapp.utils.SessionUtils;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<Book_Basic_dto> getList(Pageable pageStatus,HttpServletRequest request)
    {
        int user_id = SessionService.getUserId(request) ;
        Book[] result = null ;
        Book_Basic_dto[] retList = null ; 
        Page<Book_Basic_dto> ret = null ; 
        try{
            Page <Book> pageResult = accessBook.findByPage(pageStatus) ; 
            List<Book> bookList = pageResult.toList() ; 
            result = new Book[bookList.size()] ; 
            result = (bookList.toArray(result)) ;
            retList = new Book_Basic_dto[result.length] ; 
            int cnt = 0 ; 
            for (Book book : result ) {
                retList[cnt] = book.toBasicDto() ; 
                cnt++ ; 
            }
            ret = PageUtils.toPage(retList, pageStatus, pageResult.getTotalElements()) ; 
        }
        catch(Exception err)
        {
            System.err.println(err);
            err.printStackTrace();
            return new PageImpl<>(null); 
        }
        return ret ; 
    }

    public Page<Book_Basic_dto> searchBook(String query ,Pageable pageStatus, HttpServletRequest request)
    {
        int user_id = SessionService.getUserId(request) ;
        List<Book> result ;
        Book_Basic_dto[] arrayRet = null ; 
        Page<Book_Basic_dto> ret = null ; 
        try{
            Page <Book> pageResult = accessBook.SearchByName(query,pageStatus) ; 
            result = pageResult.toList() ; 
            arrayRet = new Book_Basic_dto[result.size()] ; 
            int cnt = 0 ; 
            for (Book book : result ) {
                arrayRet[cnt] = book.toBasicDto() ; 
                cnt++ ; 
            }
            ret = PageUtils.toPage(arrayRet, pageStatus, pageResult.getTotalElements()) ; 
        }
        catch(Exception err)
        {
            System.err.println(err);
            return null; 
        }
        return ret ; 
    }
}