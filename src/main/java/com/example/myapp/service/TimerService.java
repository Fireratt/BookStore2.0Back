package com.example.myapp.service;
import com.example.myapp.data.Cart;
import com.example.myapp.repository.AccessUser;
import com.example.myapp.utils.SessionUtils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.* ; 
@Service
@Scope(value = "session")
public interface TimerService{
    void login() ; 
    long logout() ; 
}