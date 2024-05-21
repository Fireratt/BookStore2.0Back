package com.example.myapp.data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import lombok.Data;
import java.util.Date;
import jakarta.persistence.*;
import java.util.StringTokenizer;
@Entity 
@Data
@Table(name="orderitem")
public class OrderItem {
    
    @Column(name = "order_id")
    private int order_id ; 
    @Column(name = "book_id")
    private int book_id ; 
    @Id 
    @Column(name = "orderitem_id")
    private int orderitem_id ; 


    @Column(name = "name")
    // @Transient
    private String BookName ; 
    @Column(name = "price")
    private double Price ; 
    @Column(name = "amount")
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
    // a string split by ,
    public OrderItem(String toTransform)
    {
        StringTokenizer token = new StringTokenizer(toTransform,",") ; 
        String [] strarr = new String[token.countTokens()]; 
        int i = 0 ; 

        while(token.hasMoreElements())
        {
            strarr[i++] = token.nextToken() ; 
        }

        this.book_id =Integer.parseInt(strarr[0] ); 
        BookName = strarr[1] ; 
        Price = Double.parseDouble(strarr[2]) ; 
        Amount = Integer.parseInt(strarr[3]) ;
        orderitem_id = 0 ; 
    }
    public OrderItem()
    {

    }
}