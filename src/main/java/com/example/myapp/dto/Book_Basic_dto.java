package com.example.myapp.dto;

public class Book_Basic_dto {
    // this class just contain part of the Book.java ; it is the basic information for a book
    public int bookId; 
	public String Name ; 
    public double Price ; 
    public Book_Basic_dto(int Book_Id , String iName,double iPrice)
    {
        this.bookId = Book_Id ; 
        this.Name = iName ; 
        this.Price = iPrice ; 
    }
    public Book_Basic_dto(){

    }
}

