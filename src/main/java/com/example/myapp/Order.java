package com.example.myapp;

public class Order {
    public String BookName ; 
    public double Price ; 
    public double Paid ; 
    public int Status ; // the status of the order ; // 0: sending 
    public int Code ; 
    public String Date ; 
    public int Amount ; 
    public Order(String bookName , double price , 
        double paid , int status , 
        int code , String date ,
        int amount )
    {
        BookName = bookName ; 
        Price = price ; 
        Paid = paid ; 
        Status = status  ; 
        Code = code ; 
        Date = date ; 
        Amount = amount ;
    } ; 
}