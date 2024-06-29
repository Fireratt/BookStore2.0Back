package com.example.myapp.dto;

import java.math.BigDecimal;

public class Book_Basic_dto {
    // this class just contain part of the Book.java ; it is the basic information for a book
    public int bookId; 
	public String Name ; 
    public BigDecimal Price ; 
    public String cover ; 
    public Book_Basic_dto(int Book_Id , String iName,BigDecimal iPrice , String cover)
    {
        this.bookId = Book_Id ; 
        this.Name = iName ; 
        this.Price = iPrice ; 
        this.cover = cover ; 
    }
    public Book_Basic_dto(){

    }
}

