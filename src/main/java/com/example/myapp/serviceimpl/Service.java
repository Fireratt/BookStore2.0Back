package com.example.myapp.serviceimpl;
import java.util.* ;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.* ; 
@org.springframework.stereotype.Service
public interface Service
{
    public boolean put(Object entity , HttpServletRequest request) ; 
    public boolean del(Object entity , HttpServletRequest request) ; 
    public Object get(Object entity , HttpServletRequest request) ; 
    public Object[] getList(HttpServletRequest request) ; 
}