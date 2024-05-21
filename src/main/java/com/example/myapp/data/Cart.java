package com.example.myapp.data;
import jakarta.persistence.*;
import lombok.Data;
@Data
@IdClass(Cart.class)
@Entity 
@Table(name = "cart")
public class Cart extends Object{
    @Id 
    @Column(name = "user_id")
    private int userId ; 

    @Id 
    @Column(name = "book_id")
    private int bookId ; 

    @Basic
    @Column(name = "price")
    private double bookPrice ; 

    @Basic
    @Column(name = "name")
    private String bookName ;
    public Cart(int userId, int bookId , double bookPrice , String bookName)
    {
        this.bookId = bookId ; 
        this.bookPrice = bookPrice ; 
        this.userId = userId ; 
        this.bookName = bookName; 
    }

    public Cart()
    {
        
    }
}
