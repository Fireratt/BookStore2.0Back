package com.example.myapp.data;
import com.example.myapp.dto.Cart_dto;

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

    @ManyToOne(fetch = FetchType.EAGER , cascade =  CascadeType.REFRESH)
    @JoinColumn(name = "book_id")
    private Book book ; 

    public Cart(int userId, int bookId)
    {
        this.bookId = bookId ; 
        this.userId = userId ; 
    }

    public Cart()
    {
        
    }

    public Cart_dto toCart_dto()
    {
        return new Cart_dto(userId, bookId, book.getPrice(), book.getName()) ; 
    }
}
