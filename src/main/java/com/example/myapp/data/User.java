package com.example.myapp.data ; 
import java.util.Map;
import java.util.*;
import jakarta.persistence.*;
import lombok.Data;
@Entity 
@Table(name = "user")
@Data
public class User {
    @Id
    @Column(name = "user_id")
    private String id ; 

    @Basic
    @Column(name = "name")

    private String username ; 
    private String date ; 
    private String phone ; 
    
    @Basic
    @Column(name= "mail")
    private String mail ; 
    public User(Map<String,Object> data)
    {
        id = data.get("userid").toString() ; 
        username = data.get("username").toString() ; 
        date = data.get("date").toString() ; 
        phone = data.get("phone").toString() ; 
        mail = data.get("mail").toString() ; 
    }
    // make it a Map , to better transfer
    public Map<String,String> getMap()
    {
        HashMap<String,String> data = new HashMap<String,String>() ; 
        data.put("id" , id) ; 
        data.put("username" , username) ; 
        data.put("date" , date) ; 
        data.put("phone" , phone) ; 
        data.put("mail" , mail) ; 
        return data ; 
    }

    public User()
    {
        
    }
}