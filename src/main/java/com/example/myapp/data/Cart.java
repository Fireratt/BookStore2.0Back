package com.example.myapp.data;
public class Cart extends Object{
    public int userId ; 
    public int bookId ; 
    public double bookPrice ; 
    public String bookName ;
    public Cart(int userId, int bookId , double bookPrice , String bookName)
    {
        this.bookId = bookId ; 
        this.bookPrice = bookPrice ; 
        this.userId = userId ; 
        this.bookName = bookName; 
    }
}
