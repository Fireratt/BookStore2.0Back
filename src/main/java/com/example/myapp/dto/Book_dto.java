package com.example.myapp.dto ;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Book_dto
{
    private int bookId ; 

	private String Author ; 

	private String Name ; 

    private BigDecimal Price ; 

	private String Description ; 

	private int Storage ; 

    private BigDecimal RealPrice ; 
// Byte array should encode it in the base64 format in this string
	private String cover ; 

	private String isbn ; 
    public Book_dto(int Book_Id , String iName, String iAuthor 
		, String iDescription, BigDecimal iPrice,BigDecimal real_price , int iStorage , String cover , String isbn)
	{
		this.bookId = Book_Id ; 
		this.Name = iName ; 
		this.Price = iPrice ; 
		Author = iAuthor ; 
		Description = iDescription ; 
        Storage = iStorage ; 
        RealPrice = real_price ; 
		this.cover = cover ; 
		this.isbn = isbn ; 
	}

}