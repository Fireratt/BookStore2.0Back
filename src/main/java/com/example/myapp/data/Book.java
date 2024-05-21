package com.example.myapp.data ; 
import com.example.myapp.data.Book_Basic;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity 
@Table(name = "book" ,schema = "bookdb")
public class Book extends Book_Basic
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

	public Book(int Book_Id , String iName, String iAuthor , String iDescription, double iPrice,double real_price , int iStorage)
	{
		super(Book_Id,iName,iPrice) ; 
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
}