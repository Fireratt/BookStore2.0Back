package com.example.myapp.data ;
import jakarta.persistence.*;
@Entity 
public class UserAuth {
    @Id
    @Column(name="user_id")
    public String user_id ;
    public UserAuth(String user_id) 
    {
        this.user_id = user_id ; 
    }
}