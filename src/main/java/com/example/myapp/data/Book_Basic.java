package com.example.myapp.data ; 
// this class just contain part of the Book.java ; it is the basic information for a book
public class Book_Basic {
    public int Book_Id ; 
	public String Name ; 
    public double Price ; 
    public Book_Basic(int Book_Id , String iName,double iPrice)
    {
        this.Book_Id = Book_Id ; 
        this.Name = iName ; 
        this.Price = iPrice ; 
    }
}