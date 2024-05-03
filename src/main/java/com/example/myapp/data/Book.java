package com.example.myapp.data ; 


public class Book
{
    public int Book_Id ; 
	public String Name ; 
	public String Author ; 
	public String Description ; 
	public double Price ; 
    public double Real_Price ; 
    public int Storage ; 
	public Book(int Book_Id , String iName, String iAuthor , String iDescription, double iPrice,double real_price , int iStorage)
	{
        this.Book_Id = Book_Id ; 
		Name = iName ; 
		Author = iAuthor ; 
		Description = iDescription ; 
		Price = iPrice ; 
        Storage = iStorage ; 
        Real_Price = real_price ; 
	}
}