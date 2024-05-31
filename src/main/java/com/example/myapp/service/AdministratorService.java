package com.example.myapp.service;
import com.example.myapp.dao.AccessBook;
import com.example.myapp.dao.AccessUser;
import com.example.myapp.data.Book;
import com.example.myapp.data.Cart;
import com.example.myapp.data.User;
import com.example.myapp.dto.Book_Basic_dto;
import com.example.myapp.dto.Book_dto;
import com.example.myapp.utils.SessionUtils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AdministratorService {
    @Autowired
    AccessBook accessBook ; 
    @Autowired
    AccessUser accessUser ; 
    public Book_dto[] searchBook(String toSearch, HttpServletRequest request) throws PermissionDeniedException
    {
        int user_id =Integer.parseInt(SessionUtils.readSession("user_id", request)) ; 
        // check if current User is administrator.
        boolean isAdministrator = accessUser.checkAdministrator(user_id) ; 
        if(!isAdministrator)
        {
            throw new PermissionDeniedException() ; 
        }
        Book[] result = accessBook.SearchByName(toSearch) ; 
        Book_dto[] ret = new Book_dto[result.length] ;
        int cnt = 0 ;  
        for (Book book : result) {
            ret[cnt] = book.toDto() ; 
            cnt++ ; 
        }
        return ret ; 
    }
    public boolean modifyBook(int book_id , String name , String author , int storage , String cover , HttpServletRequest request) throws PermissionDeniedException
    {
        try
        {
            int user_id =Integer.parseInt(SessionUtils.readSession("user_id", request)) ; 
            boolean isAdministrator = accessUser.checkAdministrator(user_id) ; 
            if(!isAdministrator)
            {
                throw new PermissionDeniedException() ; 
            }
            accessBook.modifyBook(book_id, name, author, storage) ;
            if(cover!=null)
            {
                accessBook.modifyCover(book_id, cover) ; 
            }
        }
        catch(Exception err)
        {
            err.printStackTrace();
            System.err.println(err);
            return false ; 
        }
        return true ; 
    }
    public class PermissionDeniedException extends Exception
    {
        public final String message = "Not the administrator , Permission Denied" ; 
    }
}
