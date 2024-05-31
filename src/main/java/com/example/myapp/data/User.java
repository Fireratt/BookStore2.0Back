package com.example.myapp.data ; 
import java.util.*;

import com.example.myapp.dto.User_dto;

import jakarta.persistence.*;
import lombok.Data;
@Entity 
@Table(name = "user")
@Data
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ; 

    @Basic
    @Column(name = "name")
    private String username ; 
    @Transient
    private String date ; 
    @Transient

    private String phone ; 
    
    @Basic
    @Column(name= "mail")
    private String mail ; 

    @Basic
    @Column(name = "administrator")
    private boolean administrator ; 
    public User(Map<String,Object> data)
    {
        id = Integer.parseInt(data.get("userid").toString()) ; 
        username = data.get("username").toString() ; 
        date = data.get("date").toString() ; 
        phone = data.get("phone").toString() ; 
        mail = data.get("mail").toString() ; 
    }
    // make it a Map , to better transfer
    public Map<String,String> getMap()
    {
        HashMap<String,String> data = new HashMap<String,String>() ; 
        data.put("id" ,id + "") ; 
        data.put("username" , username) ; 
        data.put("date" , date) ; 
        data.put("phone" , phone) ; 
        data.put("mail" , mail) ; 
        return data ; 
    }

    
    public User()
    {
        
    }

    public User_dto toDto()
    {
        return new User_dto() ; 
    }
}