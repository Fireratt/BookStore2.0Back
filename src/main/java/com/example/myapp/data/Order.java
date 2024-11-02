package com.example.myapp.data;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.myapp.dto.OrderItem_dto;
import com.example.myapp.dto.Order_dto;
import com.example.myapp.settings.OrderSettings;
import com.example.myapp.utils.FuncUtils;

import lombok.Data;
import reactor.core.publisher.Flux;

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
    private BigDecimal totalPrice ;        // will calculated when initialized 
        
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
        totalPrice = new BigDecimal(0); 
        for(int i = 0 ; i < size ; i++)
        {
            OrderItem item = orderItems.get(i) ; 
            // use outside Function to Calculate the Price
            Flux<String> result = FuncUtils.callFunctionStatic(OrderSettings.CalculatorFuncUri, OrderSettings.FunctionName, item.getAmount() + "," + item.getPrice().intValue()) ; 
            String currentPrice = result.blockFirst() ; 
            // delete the []
            currentPrice = currentPrice.substring(1 , currentPrice.length() -2) ; 
            System.out.println("<Order> Compute Price :" + currentPrice);
            totalPrice.add(new BigDecimal(currentPrice)) ; 
            totalPrice.add(item.getPrice().multiply(new BigDecimal(item.getAmount()))) ; 
        }
    }
    // append the orderItem ; recalculate the price
    public void insertOrderItem(OrderItem newItem)
    {
        totalPrice.add(newItem.getPrice().multiply(new BigDecimal(newItem.getAmount()))) ; 
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