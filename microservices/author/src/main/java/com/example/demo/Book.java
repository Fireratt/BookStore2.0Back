package com.example.demo ; 
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import lombok.Data;
@Data
public class Book
{
    private int bookId ; 

	private String Author ; 

	private String Name ; 

    private BigDecimal Price ; 

	private String Description ; 

	private int Storage ; 

    private BigDecimal RealPrice ; 

	private String isbn ; 

	private String cover ;

	private int valid ; 
	// @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
	// @JoinColumn(name="book_id") 
	// private List<Cart> carts ; 
	public Book(int Book_Id , String iName, String iAuthor , String iDescription, double iPrice,BigDecimal real_price , int iStorage)
	{
		this.bookId = Book_Id ; 
		this.Name = iName ; 
		this.Price = new BigDecimal(iPrice) ; 
		Author = iAuthor ; 
		Description = iDescription ; 
        Storage = iStorage ; 
        RealPrice = real_price ; 
	}

	public Book()
	{

	}
	public Book(String name ,double price ,String author, String description , int storage , String isbn , String cover){
		this.Name = name ; 
		this.Price = new BigDecimal(price); 
		this.Author = author ;
		this.Description = description ; 
		this.Storage = storage ; 
		this.isbn = isbn ; 
		this.cover = cover; 
		this.valid = 1 ; 
	}
}