package com.example.myapp.controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.example.myapp.BookList;
import com.example.myapp.DemoApplication;
import com.example.myapp.DemoApplication.HttpException;
import com.example.myapp.dao.*;
import com.example.myapp.service.*;
import com.example.myapp.service.OrderService.StorageNotEnoughException;
import com.example.myapp.dto.*;
import com.example.myapp.data.Order;
import com.example.myapp.data.OrderItem;
import com.example.myapp.utils.SessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.* ; 
@RestController
public class OrderController 
{
    @Autowired
    OrderService orderService ; 
    @Autowired
    BookService accessBook ; 
    @GetMapping("/order")
    public Order_dto[] getOrderList(HttpServletRequest request)
    {
        return orderService.getList(request) ; 
    }
    
    @PostMapping("/order")  // the front end should pass a Array of kv like : [book_id , amount]
    public Map<String,String> addOrder(@RequestBody List<Map<String,String>> body,HttpServletRequest request , HttpServletResponse response)
    {
        ArrayList<OrderItem> orderItems = new ArrayList<>() ; 
        int size = body.size() ; 
        for(int i = 0 ; i < size ; i++) // read all the book in the body . 
        {
            Map<String,String> singleBook = body.get(i) ; 
            int book_id =Integer.parseInt(singleBook.get("book_id")) ; 
            System.out.println("AddOrder::" + book_id);
            int amount = Integer.parseInt(singleBook.get("amount")) ; 
            System.out.println(amount);
            Book_dto bookInfo = accessBook.get(book_id , request) ; 
            OrderItem newItem = new OrderItem(book_id, bookInfo.getName(), bookInfo.getPrice(), amount) ;
            orderItems.add(newItem) ; 
        }
        Order newOrder = new Order(-1, -1, orderItems, "") ; 
        boolean result = false; 
        HashMap<String,String> ret = new HashMap<>() ; 
        try{
            result = orderService.put(newOrder, request) ;
        } catch(StorageNotEnoughException err)
        {
            result = false ; 
            System.out.println(err.book_id + "::" + StorageNotEnoughException.message) ;
            ret.put("reason" , "库存不足") ; 
            ret.put("book_id" , err.book_id + "") ; 
        }
        if(result)
            ret.put("Success","true" ) ; 
            else
            {
                ret.put("Success","false" ) ; 
            }
        return ret ; 
    }
    // the paid info
    @PostMapping("/pay")
	public Map<String,String>[] queryController(@RequestBody List<Map<String,String>> requestBody , HttpServletRequest request)
	{
        int len = requestBody.size() ; 
        Map<String,String>[] items = new Map[len] ; 
        for(int i = 0 ; i < len ; i++)
        {
            Map<String,String> singleBook = requestBody.get(i) ; 
            int book_id =Integer.parseInt(singleBook.get("book_id")) ; 
            String BookAmount = singleBook.get("amount") ; 
            System.out.println(singleBook.get("book_id")) ; 
            Book_dto bookInfo = accessBook.get(book_id , request) ; 
            HashMap<String,String> ret = new HashMap<String,String>() ; 
            ret.put("book_id","" +  book_id) ; 
            ret.put("Name" ,"" +  bookInfo.getName()) ; 
            ret.put("Price" ,"" + bookInfo.getPrice()) ; 
            ret.put("amount" , BookAmount) ; 
            items[i] = ret ; 
        }
        return items ; 
	}

    @GetMapping("/order/search")
    public Order_dto[] searchOrder(@RequestParam Map<String,String> param,  HttpServletRequest request)
    {
        String query = param.get("query") ; 
        return orderService.searchOrder(query, request) ; 
    }

    @GetMapping("/order/select")
    public Order_dto[] selectOrder(@RequestParam Map<String,String> param,  HttpServletRequest request)
    {
        String start = param.get("start") ; 
        String end = param.get("end") ; 
        System.out.println("start"+start + " ,End" + end);
        return orderService.selectOrderByDate(start, end, request) ; 
    }
}