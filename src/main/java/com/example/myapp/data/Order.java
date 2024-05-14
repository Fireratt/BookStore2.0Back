package com.example.myapp.data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import lombok.Data;
import java.util.Date;

@Data
public class Order {
    private int order_id ; 
    private int user_id ; 
    private ArrayList<OrderItem> orderItems ; 
    private double totalPrice ;        // will calculated when initialized 
    private String date ;           // YYYY-MM-DD , automatically generated
    public Order(int order_id , int user_id ,ArrayList<OrderItem> orderItems , String iDate)
    {
        // pass empty Date , initialize the date ; 
        if(iDate == "")
        {
            Date date = new Date() ; 
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd") ; 
            setDate(formatter.format(date));
        }
        else
        {
            setDate(iDate);
        }

        setOrder_id(order_id);
        setUser_id(user_id);
        setOrderItems(orderItems);

        int size = orderItems.size() ; 
        totalPrice = 0 ; 
        for(int i = 0 ; i < size ; i++)
        {
            OrderItem item = orderItems.get(i) ; 
            totalPrice = totalPrice + item.getAmount() *item.getPrice() ; 
        }
    }
    // append the orderItem 
    public void insertOrderItem(OrderItem newItem)
    {
        orderItems.add(newItem) ; 
    }
}