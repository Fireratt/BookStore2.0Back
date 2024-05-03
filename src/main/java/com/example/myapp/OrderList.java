package com.example.myapp;

import com.example.myapp.Order;
public class OrderList {
    static final Order[] Orders= 
    {
        new Order(
            "IronFlame" , 
            122.0,
            110.9,
            0,
            1,
            "1970/1/1",
            1 ) ,
            
        new Order("IronFlame" , 122.0,110.8,0,2,"1970/1/1" , 2) ,
        new Order("ACourtofWingsandRuin" , 122.0,110.7,0,3,"1970/1/1" , 3) ,
        new Order("IronFlame" , 122.0,110.6,0,4,"1970/1/1" , 4) 
    } ; 
    static Order find(String bookName)
    {
        int size = Orders.length ; 
        for (int i = 0 ; i < size ; i++)
        {
            if(bookName.equals(Orders[i].BookName))
            {
                
                return Orders[i] ; 
            }
        }
        return null; 
    }
}