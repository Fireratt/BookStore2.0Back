package com.example.myapp.controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.example.myapp.dao.AccessAccount;
import com.example.myapp.dao.AccessCart;
import com.example.myapp.data.Cart;
import com.example.myapp.data.UserAuth;
import com.example.myapp.dto.Cart_dto;
import com.example.myapp.service.CartService;
import com.example.myapp.service.SessionService;
import com.example.myapp.utils.SessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.* ; 
@RestController
public class CartController {
    @Autowired
    CartService cartService ; 

    @GetMapping("/cart")
    public Cart_dto[] getCarts(HttpServletRequest request)
    {
        return cartService.getList(request) ; 
    }

    @PutMapping("/cart")
    public Map<String,String> addCart(@RequestBody Map<String,String>body,HttpServletRequest request)
    {
        HashMap<String,String> ret = new HashMap<String , String>() ; 
        int book_id =Integer.parseInt(body.get("book_id")) ;
        System.out.println("PutCart Get:" + book_id); 
        if(cartService.put(book_id, request))
        {
            ret.put("State" , "true") ; 
        }
        else
        {
            ret.put("State" , "false") ; 
        }
        return ret ; 
    }

    @DeleteMapping("/cart")
    public Map<String,String> delCart(@RequestBody Map<String,String>body,HttpServletRequest request)
    {
        HashMap<String,String> ret = new HashMap<String , String>() ; 
        int book_id =Integer.parseInt(body.get("book_id")) ; 
        if(cartService.del(book_id, request))
        {
            ret.put("State" , "true") ; 
        }
        else
        {
            ret.put("State" , "false") ; 
        }
        return ret ; 
    }
}
