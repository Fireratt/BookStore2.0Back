package com.example.myapp.dto;

import java.util.*;

import com.example.myapp.data.Order;
import com.example.myapp.data.OrderItem;

import lombok.Data;

@Data
public class OrderStatistic_dto {

    private int priceSum ; 

    private int numberSum ; 

    private HashMap<String,Integer> books ; 

    public OrderStatistic_dto(Order[] orders)
    {
        books = new HashMap<>() ; 
        priceSum = 0 ; 
        numberSum = 0 ; 
        int length = orders.length ; 
        System.out.println("StatisticDto Length:" + length);
        for(int i = 0 ; i < length ; i++)
        {
            List<OrderItem> orderItems = orders[i].getOrderItems() ; 
            for (OrderItem orderItem : orderItems) {
                priceSum += orderItem.getPrice() * orderItem.getAmount() ; 
                numberSum += orderItem.getAmount()  ; 
                String bookName = orderItem.getBook().getName() ; 
                System.out.println(bookName);
                if(books.containsKey(bookName))
                {
                    books.put(bookName, books.get(bookName) + Integer.valueOf(orderItem.getAmount())); 
                }
                else
                {
                    books.put(bookName,Integer.valueOf(orderItem.getAmount())); 
                }
            }
        }
    }
}