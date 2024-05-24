package com.example.myapp.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.myapp.data.OrderItem;

import lombok.Data;

@Data
public class Order_dto {

    private int orderId ; 
    
    private int userId ; 

    private String date ;           // YYYY-MM-DD , automatically generated
 
    private List<OrderItem_dto> orderItems ; 

    private double totalPrice ;        // will calculated when initialized 
 
    public Order_dto(int order_id , int user_id ,List<OrderItem_dto> orderItems , String iDate)
    {
        // pass empty Date , initialize the date ; 
        if(iDate == "")
        {
            Date date = new Date() ; 
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd") ; 
            setDate(formatter.format(date));
        }
        else
        {
            setDate(iDate);
        }

        setOrderId(order_id);
        setUserId(user_id);
        setOrderItems(orderItems);

        int size = orderItems.size() ; 
        totalPrice = 0 ; 
        for(int i = 0 ; i < size ; i++)
        {
            OrderItem_dto item = orderItems.get(i) ; 
            totalPrice = totalPrice + item.getAmount() *item.getPrice() ; 
        }
    }
}
