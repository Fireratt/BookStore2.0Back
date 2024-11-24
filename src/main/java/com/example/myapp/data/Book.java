package com.example.myapp.data ; 
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

import com.example.myapp.dto.BookRank;
import com.example.myapp.dto.Book_Basic_dto;
import com.example.myapp.dto.Book_dto;
import com.example.myapp.utils.ByteUtils;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import lombok.Data;
@Data
@Entity 
@Table(name = "book" ,schema = "bookdb")
// Add the stored procedure
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "getBookRank" , procedureName="get_book_rank" , parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN , name = "start" , type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN , name = "end" , type = String.class)
	})
})
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

	@Column(name="price" , columnDefinition = "decimal(10,2)") 
    private BigDecimal Price ; 

	@Column(name="description") 
	private String Description ; 

	@Column(name="storage") 
	private int Storage ; 

	// @Column(name="real_price") 
	@Transient
    private BigDecimal RealPrice ; 

	@Column(name = "isbn")
	private String isbn ; 

	// @Basic(fetch=FetchType.LAZY)
	// @Lob
	// @Column(name="cover")
	@Transient
	private String cover ;

	@Column(name ="valid")
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
	public Book_Basic_dto toBasicDto()
	{
		return new Book_Basic_dto(bookId,  Name , Price , cover) ; 
	}
	
	public Book_dto toDto()
	{

		return new Book_dto(bookId, Name, Author, Description, Price ,RealPrice, Storage , cover , isbn) ; 
	}
}