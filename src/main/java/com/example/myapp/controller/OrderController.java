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
import com.example.myapp.utils.StringUtils;

import org.apache.catalina.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import jakarta.servlet.http.* ; 
@RestController
public class OrderController 
{
    static final String DEFAULT_START = "1970-01-01" ; 
    static final String DEFAULT_END = "2035-01-01" ; 
    @Autowired
    OrderService orderService ; 
    @Autowired
    BookService accessBook ; 
    @Autowired
	private KafkaTemplate<String,String> kafkaTemplate ; 

    @GetMapping("/order")
    public Order_dto[] getOrderList(HttpServletRequest request)
    {
        return orderService.getList(request) ; 
    }
    
    @PostMapping("/order")  // the front end should pass a Array of kv like : [book_id , amount]
    public Map<String,String> addOrder(@RequestBody List<Map<String,String>> body,HttpServletRequest request , HttpServletResponse response)
    {
        HashMap<String,String> ret = new HashMap<>() ; 
        int user_id = SessionService.getUserId(request) ;
        // add a user_id add the head to mark whose order 
        try{
            kafkaTemplate.send("Order" ,  user_id +";" +StringUtils.form(body.toArray())) ; 
        }
        catch(Exception e){
            e.printStackTrace();
            ret.put("reason" , "下订单失败") ; 
            ret.put("Success","false" ) ; 
            return ret ; 
        }
        ret.put("Success","true" ) ; 
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
        if(start == "")
        {
            start = DEFAULT_START ;  // default value ; 
        }
        String end = param.get("end") ; 
        if(end == "")
        {
            end = DEFAULT_END ;  // default value ; 
        }
        System.out.println("start"+start + " ,End" + end);
        return orderService.selectOrderByDate(start, end, request) ; 
    }

    @GetMapping("/order/statistic")
    public OrderStatistic_dto countOrder(@RequestParam Map<String,String> param ,  HttpServletRequest request)
    {
        String start = param.get("start") ; 
        if(start == "")
        {
            start = DEFAULT_START ;  // default value ; 
        }
        String end = param.get("end") ; 
        if(end == "")
        {
            end = DEFAULT_END ;  // default value ; 
        }
        System.out.println("BigDecimal:" + orderService.countOrder(start, end, request).getPriceSum().toString() ); 
        return orderService.countOrder(start, end, request) ; 
    }
}