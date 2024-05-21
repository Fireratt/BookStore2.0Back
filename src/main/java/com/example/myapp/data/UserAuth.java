package com.example.myapp.data ;
import jakarta.persistence.*;
import lombok.Data;
@Entity 
@Data
public class UserAuth {
    @Id
    @Column(name="user_id")
    private String user_id ;

    @Column(name="password")
    private String password ; 
    public UserAuth(String user_id) 
    {
        this.user_id = user_id ; 
    }
    public UserAuth()
    {
        
    }
}