package com.example.myapp.service ;

import com.example.myapp.data.Cart;
import com.example.myapp.dto.Cart_dto;
import com.example.myapp.utils.SessionUtils;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.* ; 
@Service
public interface CartService {

    public boolean del(Object entity, HttpServletRequest request) ;

    public boolean put(Object entity, HttpServletRequest request) ; 

    public Cart get(Object entity, HttpServletRequest request) ; 

    public Cart_dto[] getList(HttpServletRequest request) ; 
}