package com.example.myapp.data;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import lombok.Data;
import java.util.Date;
import jakarta.persistence.*;
import java.util.StringTokenizer;

import com.example.myapp.dto.OrderItem_dto;
@Entity 
@Data
@Table(name="orderitem")
public class OrderItem {
    
    @Column(name = "order_id" , insertable = false , updatable = false)
    private int order_id ; 
    @Column(name = "book_id" , insertable = false , updatable = false)
    private int book_id ; 
    @Id 
    @Column(name = "orderitem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderitem_id ; 


    // @Column(name = "name")
    @Transient
    private String BookName ; 

    @Column(name = "price" , columnDefinition = "decimal(10,2)")
    private BigDecimal Price ; 

    @Column(name = "amount")
    private int Amount ; 

    @ManyToOne(cascade = CascadeType.REFRESH , fetch = FetchType.EAGER) 
    @JoinColumn(name="book_id")
    private Book book ;

    // @ManyToOne(cascade = CascadeType.REFRESH , fetch = FetchType.LAZY) 
    // @JoinColumn(name="order_id")
    // private Order order ; 

    public OrderItem(int book_id , String bookName , BigDecimal price , 
        int amount )
    {
        this.book_id = book_id ; 
        BookName = bookName ; 
        Price =price ; 
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
        Price = new BigDecimal(Double.parseDouble(strarr[2])) ; 
        Amount = Integer.parseInt(strarr[3]) ;
        orderitem_id = 0 ; 
    }
    public OrderItem()
    {

    }

    public OrderItem_dto toDto()
    {
        return new OrderItem_dto(BookName, Price, Amount) ; 
    }
}