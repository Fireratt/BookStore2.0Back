package com.example.myapp.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "BookCover")
public class BookCover {
    @Id
    private int id ; 

    private String coverBase64 ; 
    
    public BookCover(){
        coverBase64 = "" ; 
    }

    public String getBase64(){
        return coverBase64 ; 
    }

    public BookCover(int id , String coverBase64){
        this.id = id ; 
        this.coverBase64 = coverBase64; 
    }

    public void setBase64(String coverBase64){
        this.coverBase64 = coverBase64 ; 
    }
}
