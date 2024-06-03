package com.example.myapp.dto;

public class Book_Basic_dto {
    // this class just contain part of the Book.java ; it is the basic information for a book
    public int bookId; 
	public String Name ; 
    public double Price ; 
    public String cover ; 
    public Book_Basic_dto(int Book_Id , String iName,double iPrice , String cover)
    {
        this.bookId = Book_Id ; 
        this.Name = iName ; 
        this.Price = iPrice ; 
        this.cover = cover ; 
    }
    public Book_Basic_dto(){

    }
}

