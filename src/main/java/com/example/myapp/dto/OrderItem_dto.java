package com.example.myapp.dto;

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

    private double Price ; 

    private int Amount ; 



    public OrderItem_dto( String bookName , double price , 
        int amount )
    {
        BookName = bookName ; 
        Price = price ; 
        Amount = amount ;
    } ; 
}
