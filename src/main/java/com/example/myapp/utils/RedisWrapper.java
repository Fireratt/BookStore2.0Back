package com.example.myapp.utils;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate ; 

@Component
public class RedisWrapper {
    @Autowired
    RedisTemplate redisTemplate ; 

    public Object get(String key){
        try{
            return redisTemplate.opsForValue().get(key) ; 
        }catch(Exception e){
            System.err.println("Cant Connect to Redis , Use Storage Instead of Cache to Run");
            return null ; 
        }
    }

    public void set(String key , String value){
        try{
            redisTemplate.opsForValue().set(key,value) ; 
        }catch(Exception e){
            System.err.println("Cant Connect to Redis , Use Storage Instead of Cache to Run");
            return ; 
        }
    }

    public boolean delete(String key){
        try{
            return redisTemplate.delete(key);
        }catch(Exception e){
            System.err.println("Cant Connect to Redis , Use Storage Instead of Cache to Run");
            return false; 
        }
    }
}
