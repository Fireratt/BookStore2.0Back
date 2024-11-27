package com.example.myapp.data;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import lombok.Data;
import java.util.Date;
import jakarta.persistence.*;
import java.util.StringTokenizer;

@Entity 
@Data
@Table(name="tag")
public class Tag{
    @Id
    private int tagentry ; 

    @Column(name = "tagid")
    private int tagid ;

    @Column(name = "book_id", insertable = false , updatable = false)
    private int book_id ; 

    @ManyToOne(cascade = CascadeType.REFRESH , fetch = FetchType.LAZY) 
    @JoinColumn(name="book_id")
    private Book book ;

    public Tag(int tagId , int bookId){
        this.tagid = tagId ; 
        this.book_id = bookId ; 
    }

    public Tag(){
        
    }
}