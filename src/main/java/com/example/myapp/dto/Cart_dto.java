package com.example.myapp.dto;

import java.math.BigDecimal;

import com.example.myapp.data.Book;

import lombok.Data;
@Data
public class Cart_dto {
    private int userId ; 

    private int bookId ; 

    private BigDecimal bookPrice ; 

    private String bookName ;

    private String cover ; 
    public Cart_dto(int userId, int bookId , BigDecimal bookPrice , String bookName , String cover)
    {
        this.bookId = bookId ; 
        this.bookPrice = bookPrice ; 
        this.userId = userId ; 
        this.bookName = bookName; 
        this.cover = cover ; 
    }
}
