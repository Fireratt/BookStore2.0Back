package com.example.myapp.data;
import java.text.SimpleDateFormat;
import java.util.List;

import com.example.myapp.dto.OrderItem_dto;
import com.example.myapp.dto.Order_dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import jakarta.persistence.*;
@Entity 
@Data
@Table(name="order_")
public class Order {
    @Id
    @Column(name="order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId ; 
    
    @Basic
    @Column(name="user_id")
    private int userId ; 

    @Column(name = "order_time")
    private String date ;           // YYYY-MM-DD , automatically generated

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER) 
    @JoinColumn(name="order_id")
    private List<OrderItem> orderItems ; 

    // @ManyToOne(cascade = CascadeType.REFRESH , fetch = FetchType.EAGER)
    // @JoinColumn(name="user_id")
    // private User user ; 

    @Transient
    private double totalPrice ;        // will calculated when initialized 
 
    public Order(int order_id , int user_id ,List<OrderItem> orderItems , String iDate)
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

    public Order()
    {
    }

    public Order_dto toDto()
    {
        ArrayList<OrderItem_dto> itemList = new ArrayList<>(orderItems.size()) ; 
        int cnt = 0 ; 
        for (OrderItem orderItem : orderItems) {
            itemList.add(new OrderItem_dto(orderItem.getBook().getName() , orderItem.getPrice() , orderItem.getAmount())) ; 
            cnt++ ;
        }
        return new Order_dto(orderId, userId, itemList, date) ; 
    }
}