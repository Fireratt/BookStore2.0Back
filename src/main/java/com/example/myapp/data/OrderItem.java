package com.example.myapp.data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import lombok.Data;
import java.util.Date;
import jakarta.persistence.*;
@Entity 
@Data
public class OrderItem {

    @Id 
    @Column(name = "orderitem_id")
    private int orderitem_id ; 
    
    private int book_id ; 
    private String BookName ; 
    private double Price ; 
    private int Amount ; 
    public OrderItem(int book_id , String bookName , double price , 
        int amount )
    {
        this.book_id = book_id ; 
        BookName = bookName ; 
        Price = price ; 
        Amount = amount ;
        orderitem_id = 0 ; 
    } ; 
}