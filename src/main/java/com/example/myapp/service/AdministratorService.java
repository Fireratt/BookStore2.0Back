package com.example.myapp.service;
import com.example.myapp.data.Book;
import com.example.myapp.data.Cart;
import com.example.myapp.data.Order;
import com.example.myapp.data.User;
import com.example.myapp.dto.BookRank;
import com.example.myapp.dto.Book_Basic_dto;
import com.example.myapp.dto.Book_dto;
import com.example.myapp.dto.Order_dto;
import com.example.myapp.utils.SessionUtils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
@Service
public interface AdministratorService {

    public Book_dto[] searchBook(String toSearch, HttpServletRequest request) throws PermissionDeniedException ; 

    public boolean modifyBook(int book_id , String name , 
        String author , int storage , String cover , 
        String isbn , double price , HttpServletRequest request) throws PermissionDeniedException ; 

    public boolean addBook( String name , String author 
        , int storage , double price,String description, String isbn,String cover , HttpServletRequest request) throws PermissionDeniedException ; 

    public boolean deleteBook(int book_id, HttpServletRequest request)throws PermissionDeniedException ; 

    public List<User> getUserInfo(HttpServletRequest request)throws PermissionDeniedException ;  

    public Page<Order_dto> getAllOrder(Pageable pageStatus , HttpServletRequest request)throws PermissionDeniedException ; 
    public Page<Order_dto> searchAllOrder(String query,Pageable pageStatus , HttpServletRequest request) throws PermissionDeniedException; 
    public Page<Order_dto> selectAllOrderByDate(String start , String end , Pageable pageStatus , HttpServletRequest request) throws PermissionDeniedException; 

    public boolean banUser(int userId , HttpServletRequest request)throws PermissionDeniedException ; 
    
    public boolean unbanUser(int userId , HttpServletRequest request)throws PermissionDeniedException ; 

    public List<User> getUserRanking(String start , String end , HttpServletRequest request) throws PermissionDeniedException ; 

    public List<Map> getBookRank(String start , String end , Pageable pageStatus , HttpServletRequest request) throws PermissionDeniedException ; 

    public class PermissionDeniedException extends Exception
    {
        public final String message = "Not the administrator , Permission Denied" ; 
    }

    
}
