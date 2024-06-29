package com.example.myapp.dto;

import java.math.BigDecimal;

import com.example.myapp.data.Book;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Data
public class OrderItem_dto {

    private String BookName ; 

    private BigDecimal Price ; 

    private int Amount ; 



    public OrderItem_dto( String bookName , BigDecimal price , 
        int amount )
    {
        BookName = bookName ; 
        Price = price ; 
        Amount = amount ;
    } ; 
}
