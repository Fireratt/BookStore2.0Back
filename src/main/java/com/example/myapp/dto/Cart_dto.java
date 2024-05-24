package com.example.myapp.dto;

import com.example.myapp.data.Book;

import lombok.Data;
@Data
public class Cart_dto {
    private int userId ; 

    private int bookId ; 

    private double bookPrice ; 

    private String bookName ;
    public Cart_dto(int userId, int bookId , double bookPrice , String bookName)
    {
        this.bookId = bookId ; 
        this.bookPrice = bookPrice ; 
        this.userId = userId ; 
        this.bookName = bookName; 
    }
}
