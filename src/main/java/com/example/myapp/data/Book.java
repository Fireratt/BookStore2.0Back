package com.example.myapp.data ; 

import com.example.myapp.data.Book_Basic;
public class Book extends Book_Basic
{
    public int Book_Id ; 
	public String Name ; 
    public double Price ; 
	public String Author ; 
	public String Description ; 
	
    public double Real_Price ; 
    public int Storage ; 
	public Book(int Book_Id , String iName, String iAuthor , String iDescription, double iPrice,double real_price , int iStorage)
	{
		super(Book_Id,iName,iPrice) ; 
		Author = iAuthor ; 
		Description = iDescription ; 
        Storage = iStorage ; 
        Real_Price = real_price ; 
	}
}