package com.example.myapp.data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import lombok.Data;
import java.util.Date;
import jakarta.persistence.*;
@Entity 
@Data
@Table(name="order_")
public class Order {
    @Id
    @Column(name="order_id")
    @GeneratedValue
    private int orderId ; 
    
    @Basic
    @Column(name="user_id")
    private int userId ; 

    @Column(name = "order_time")
    private String date ;           // YYYY-MM-DD , automatically generated
    @Transient
    private ArrayList<OrderItem> orderItems ; 
    @Transient
    private double totalPrice ;        // will calculated when initialized 
 
    public Order(int order_id , int user_id ,ArrayList<OrderItem> orderItems , String iDate)
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
            OrderItem item = orderItems.get(i) ; 
            totalPrice = totalPrice + item.getAmount() *item.getPrice() ; 
        }
    }
    // append the orderItem ; recalculate the price
    public void insertOrderItem(OrderItem newItem)
    {
        totalPrice = totalPrice + newItem.getAmount() *newItem.getPrice() ; 
        orderItems.add(newItem) ; 
    }

    Order()
    {
        orderItems = new ArrayList<>();
    }
}