package com.example.myapp.data ; 
import jakarta.persistence.*;
import java.util.List;

import com.example.myapp.dto.Book_Basic_dto;
import com.example.myapp.dto.Book_dto;
import com.example.myapp.utils.ByteUtils;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import lombok.Data;
@Data
@Entity 
@Table(name = "book" ,schema = "bookdb")
public class Book
{
	@Id 
	@Column(name="book_id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId ; 

	@Column(name="author") 
	private String Author ; 

	@Column(name="name") 
	private String Name ; 

	@Column(name="price") 
    private double Price ; 

	@Column(name="description") 
	private String Description ; 

	@Column(name="storage") 
	private int Storage ; 

	@Column(name="real_price") 
    private double RealPrice ; 

	@Basic(fetch=FetchType.LAZY)
	@Lob
	@Column(name="cover")
	private String cover ;
	// @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
	// @JoinColumn(name="book_id") 
	// private List<Cart> carts ; 
	public Book(int Book_Id , String iName, String iAuthor , String iDescription, double iPrice,double real_price , int iStorage)
	{
		this.bookId = Book_Id ; 
		this.Name = iName ; 
		this.Price = iPrice ; 
		Author = iAuthor ; 
		Description = iDescription ; 
        Storage = iStorage ; 
        RealPrice = real_price ; 
	}

	public Book()
	{

	}

	public Book_Basic_dto toBasicDto()
	{
		return new Book_Basic_dto(bookId,  Name , Price) ; 
	}
	
	public Book_dto toDto()
	{

		return new Book_dto(bookId, Name, Author, Description, Price ,RealPrice, Storage , cover) ; 
	}
}