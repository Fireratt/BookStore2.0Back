package com.example.myapp.controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.example.myapp.BookList;
import com.example.myapp.DemoApplication;
import com.example.myapp.DemoApplication.HttpException;
import com.example.myapp.dao.AccessAccount;
import com.example.myapp.dao.AccessBook;
import com.example.myapp.data.Book;
import com.example.myapp.data.Book_Basic;
import com.example.myapp.data.Cart;
import com.example.myapp.data.Order;
import com.example.myapp.data.OrderItem;
import com.example.myapp.data.UserAuth;
import com.example.myapp.dto.Order_dto;
import com.example.myapp.service.BookService;
import com.example.myapp.service.CartService;
import com.example.myapp.service.OrderService;
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
    public Map<String,String> addOrder(@RequestBody List<Map<String,String>> body,HttpServletRequest request)
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
            Book bookInfo = accessBook.get(book_id , request) ; 
            OrderItem newItem = new OrderItem(book_id, bookInfo.getName(), bookInfo.getPrice(), amount) ;
            orderItems.add(newItem) ; 
        }
        Order newOrder = new Order(-1, -1, orderItems, "") ; 
        boolean result = orderService.put(newOrder, request) ; 
        HashMap<String,String> ret = new HashMap<>() ; 
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
            Book bookInfo = accessBook.get(book_id , request) ; 
            HashMap<String,String> ret = new HashMap<String,String>() ; 
            ret.put("book_id","" +  book_id) ; 
            ret.put("Name" ,"" +  bookInfo.getName()) ; 
            ret.put("Price" ,"" + bookInfo.getPrice()) ; 
            ret.put("amount" , BookAmount) ; 
            items[i] = ret ; 
        }
        return items ; 
	}
}