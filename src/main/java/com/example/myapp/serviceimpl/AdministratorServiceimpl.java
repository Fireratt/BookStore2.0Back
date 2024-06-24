package com.example.myapp.serviceimpl;
import com.example.myapp.dao.Bookdao;
import com.example.myapp.dao.Orderdao;
import com.example.myapp.dao.Userdao;
import com.example.myapp.data.Book;
import com.example.myapp.data.Cart;
import com.example.myapp.data.Order;
import com.example.myapp.data.User;
import com.example.myapp.dto.Book_Basic_dto;
import com.example.myapp.dto.Book_dto;
import com.example.myapp.dto.Order_dto;
import com.example.myapp.service.AdministratorService;
import com.example.myapp.utils.PageUtils;
import com.example.myapp.utils.SessionUtils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.example.myapp.service.SessionService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class AdministratorServiceimpl implements AdministratorService{
    @Autowired
    Bookdao accessBook ; 
    @Autowired
    Userdao accessUser ; 
    @Autowired
    Orderdao accessOrder ; 
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
    public boolean modifyBook(int book_id , String name , 
        String author , int storage , String cover ,
        String isbn , double price , HttpServletRequest request) throws PermissionDeniedException
    {

            int user_id =Integer.parseInt(SessionUtils.readSession("user_id", request)) ; 
            boolean isAdministrator = accessUser.checkAdministrator(user_id) ; 
            if(!isAdministrator)
            {
                throw new PermissionDeniedException() ; 
            }
        try
        {
            accessBook.modifyBook(book_id, name, author, storage , isbn , price) ;
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
    public boolean addBook( String name , String author 
        , int storage , double price,String description, String isbn,String cover , HttpServletRequest request) throws PermissionDeniedException
    {

            int user_id =Integer.parseInt(SessionUtils.readSession("user_id", request)) ; 
            boolean isAdministrator = accessUser.checkAdministrator(user_id) ; 
            if(!isAdministrator)
            {
                throw new PermissionDeniedException() ; 
            }
        try
        {
            accessBook.addBook(name, price, author, description ,storage, isbn, cover) ; 
        }
        catch(Exception err)
        {
            err.printStackTrace();
            System.err.println(err);
            return false ; 
        }
        return true ; 
    }

    public boolean deleteBook(int book_id, HttpServletRequest request)throws PermissionDeniedException
    {

            int user_id =Integer.parseInt(SessionUtils.readSession("user_id", request)) ; 
            boolean isAdministrator = accessUser.checkAdministrator(user_id) ; 
            if(!isAdministrator)
            {
                throw new PermissionDeniedException() ; 
            }
        try
        {
            if(accessBook.deleteBook(book_id)>0)
            {
                return true ; 
            } 
            else
            {
                return false ; 
            }
        }
        catch(Exception err)
        {
            err.printStackTrace();
            System.err.println(err);
            return false ; 
        }
    }
    public List<User> getUserInfo(HttpServletRequest request)throws PermissionDeniedException
    {

            int user_id =Integer.parseInt(SessionUtils.readSession("user_id", request)) ; 
            boolean isAdministrator = accessUser.checkAdministrator(user_id) ; 
            if(!isAdministrator)
            {
                throw new PermissionDeniedException() ; 
            }
        try
        {
            return accessUser.getUserInfo() ; 
        }
        catch(Exception err)
        {
            err.printStackTrace();
            System.err.println(err);
            return new ArrayList<>() ; 
        }
    }

    public Page<Order_dto> getAllOrder(Pageable pageStatus , HttpServletRequest request)throws PermissionDeniedException
    {

            int user_id =Integer.parseInt(SessionUtils.readSession("user_id", request)) ; 
            boolean isAdministrator = accessUser.checkAdministrator(user_id) ; 
            if(!isAdministrator)
            {
                throw new PermissionDeniedException() ; 
            }
        Order_dto[] retList = null ; 
        Page<Order_dto> ret = null ; 
        try
        {
            Page<Order> orders = accessOrder.getAllOrder(pageStatus) ; 
            List<Order> orderList = orders.toList() ; 
            retList = new Order_dto[orderList.size()] ; 
            int cnt = 0 ; 
            for (Order order : orderList) {
                retList[cnt] = order.toDto() ;
                cnt ++ ; 
            }
            ret = PageUtils.toPage(retList, pageStatus, orders.getTotalElements()) ; 
            return ret ; 
        }
        catch(Exception err)
        {
            err.printStackTrace();
            System.err.println(err);
            return new PageImpl<>(null) ; 
        }
    }

    public Page<Order_dto> searchAllOrder(String query,Pageable pageStatus , HttpServletRequest request) throws PermissionDeniedException
    {

            int user_id =Integer.parseInt(SessionUtils.readSession("user_id", request)) ; 
            boolean isAdministrator = accessUser.checkAdministrator(user_id) ; 
            if(!isAdministrator)
            {
                throw new PermissionDeniedException() ; 
            }
        Order_dto[] retList = null ; 
        Page<Order_dto> ret = null ; 
        try
        {
            Page<Order> orders = accessOrder.searchAllOrder(query, pageStatus)  ; 
            List<Order> orderList = orders.toList() ; 
            retList = new Order_dto[orderList.size()] ; 
            int cnt = 0 ; 
            for (Order order : orderList) {
                retList[cnt] = order.toDto() ;
                cnt ++ ; 
            }
            ret = PageUtils.toPage(retList, pageStatus, orders.getTotalElements()) ; 
            return ret ; 
        }
        catch(Exception err)
        {
            err.printStackTrace();
            System.err.println(err);
            return new PageImpl<>(null) ; 
        }
    }

    public Page<Order_dto> selectAllOrderByDate(String start , String end , Pageable pageStatus , HttpServletRequest request) throws PermissionDeniedException
    {

            int user_id =Integer.parseInt(SessionUtils.readSession("user_id", request)) ; 
            boolean isAdministrator = accessUser.checkAdministrator(user_id) ; 
            if(!isAdministrator)
            {
                throw new PermissionDeniedException() ; 
            }
        Order_dto[] retList = null ; 
        Page<Order_dto> ret = null ; 
        try
        {
            Page<Order> orders = accessOrder.selectAllOrderByDate(start, end, pageStatus) ; 
            List<Order> orderList = orders.toList() ; 
            retList = new Order_dto[orderList.size()] ; 
            int cnt = 0 ; 
            for (Order order : orderList) {
                retList[cnt] = order.toDto() ;
                cnt ++ ; 
            }
            ret = PageUtils.toPage(retList, pageStatus, orders.getTotalElements()) ; 
            return ret ; 
        }
        catch(Exception err)
        {
            err.printStackTrace();
            System.err.println(err);
            return new PageImpl<>(null) ; 
        }
    }

    public boolean banUser(int userId , HttpServletRequest request)throws PermissionDeniedException
    {

            int current_id =Integer.parseInt(SessionUtils.readSession("user_id", request)) ; 
            boolean isAdministrator = accessUser.checkAdministrator(current_id) ; 
            if(!isAdministrator)
            {
                throw new PermissionDeniedException() ; 
            }
        try
        {
            return accessUser.banUser(userId)==1 ; 
        }
        catch(Exception err)
        {
            err.printStackTrace();
            System.err.println(err);
            return false ; 
        }
    }
    public boolean unbanUser(int userId , HttpServletRequest request)throws PermissionDeniedException
    {
        int current_id =Integer.parseInt(SessionUtils.readSession("user_id", request)) ; 
        boolean isAdministrator = accessUser.checkAdministrator(current_id) ; 
        if(!isAdministrator)
        {
            throw new PermissionDeniedException() ; 
        }
    try
    {
        return accessUser.unbanUser(userId)==1 ; 
    }
    catch(Exception err)
    {
        err.printStackTrace();
        System.err.println(err);
        return false ; 
    }
    }
    public Page<Book_Basic_dto> getBookRanking(int pageNumber , String start , String end , HttpServletRequest request) throws PermissionDeniedException
    {

        int user_id =Integer.parseInt(SessionUtils.readSession("user_id", request)) ; 
        boolean isAdministrator = accessUser.checkAdministrator(user_id) ; 
        if(!isAdministrator)
        {
            throw new PermissionDeniedException() ; 
        }
        try
        {
            return new PageImpl<>(null) ; 
        }
        catch(Exception err)
        {
            err.printStackTrace();
            System.err.println(err);
            return new PageImpl<>(null) ; 
        }
    }

    public List<User> getUserRanking(String start , String end , HttpServletRequest request) throws PermissionDeniedException
    {

            int user_id =Integer.parseInt(SessionUtils.readSession("user_id", request)) ; 
            boolean isAdministrator = accessUser.checkAdministrator(user_id) ; 
            if(!isAdministrator)
            {
                throw new PermissionDeniedException() ; 
            }
        try
        {
            return new ArrayList<User>() ; 
        }
        catch(Exception err)
        {
            err.printStackTrace();
            System.err.println(err);
            return new ArrayList<User>() ; 
        }
    }
    
}
