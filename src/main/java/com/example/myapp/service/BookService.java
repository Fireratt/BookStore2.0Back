package com.example.myapp.service;
import com.example.myapp.data.Book;
import com.example.myapp.data.Cart;
import com.example.myapp.dto.Book_Basic_dto;
import com.example.myapp.dto.Book_dto;
import com.example.myapp.utils.SessionUtils;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.* ; 
@Service
public interface BookService {
    final int PAGE_SIZE = 10 ; 
    public boolean del(Object entity, HttpServletRequest request) ; 


    public boolean put(Object entity, HttpServletRequest request) ; 


    public Book_dto get(Object entity, HttpServletRequest request) ; 

    public Page<Book_Basic_dto> getList(Pageable pageStatus,HttpServletRequest request) ; 
    
    public Page<Book_Basic_dto> searchBook(String query ,Pageable pageStatus, HttpServletRequest request) ; 

}