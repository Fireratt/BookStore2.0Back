package com.example.myapp.utils;

import org.springframework.stereotype.Component;

import com.example.myapp.dao.Bookdao;
import com.example.myapp.data.Book;
import com.example.myapp.repository.AccessBook;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import io.micrometer.common.lang.Nullable;

import java.util.Set; 
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledExecutorService.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ScanOptions ; 
import org.springframework.data.redis.core.RedisTemplate ;
import org.springframework.data.redis.core.ScanOptions.ScanOptionsBuilder ; 
import org.springframework.data.redis.core.* ; 

import com.alibaba.fastjson2.JSON ; 
@Component
public class RedisWrapper {
    @Autowired
    RedisTemplate redisTemplate ; 
    @Autowired
    private AccessBook accessBook ; 
    final int DefaultInterval = 1000 ;
    final int TotalTime = 10000 ; 
    private class Timer implements Runnable{

        RedisWrapper parent ; 
        AtomicInteger CurrentInterval ; 
        final int DefaultInterval; 
        final int TotalTime ; 
        public Timer(RedisWrapper parent , int DefaultInterval , int TotalTime){
            this.parent = parent ; 
            this.DefaultInterval = DefaultInterval ; 
            this.TotalTime = TotalTime ; 
            this.CurrentInterval = new AtomicInteger(TotalTime);
        }
        // reset the timer to Default
        public void Reset() {
            CurrentInterval.set(TotalTime);
        }
        @Override
        public void run(){
            int currentTime = CurrentInterval.addAndGet(-DefaultInterval) ; 
            if(currentTime <= 0){
                Reset() ; 
                // write to the database
                System.out.println("Save the redis to Mysql");
                parent.save(); 
            }
        }
    }

    public RedisWrapper(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);  
        // per 10 second , flush the redis
        scheduler.scheduleAtFixedRate(new Timer(this , DefaultInterval , TotalTime)
        , 0, DefaultInterval, TimeUnit.MILLISECONDS) ; 
    }
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
    // will save the redis to mysql
    public void save(){
        // first scan the dirty keys 
        ScanOptions options= ScanOptions.scanOptions().match("*_dirty").build() ;
        Cursor<String> cursor =  redisTemplate.scan(options) ; 
        ArrayList<Book> toSave = new ArrayList<Book>() ; 
        while(cursor.hasNext()){
            String key = cursor.next() ; 
            // dirty page need to write 
            if(Integer.parseInt((String)(get(key))) == 1){
                int location = key.indexOf("_", 0) ; 
                String valueKeyString = key.substring(0, location-1) ; 
                System.out.println("DirtyKey finded :" + valueKeyString) ; 
                addDirtyBit(valueKeyString);
                String content = (String)get(valueKeyString) ; 
                toSave.add((Book)JSON.parseObject(content , Book.class)) ; 
            }
        }
        accessBook.saveAll(toSave) ; 
    }   
    // record if the data in the redis have been modified ;
    public void setDirty(String key){
        String dirtyKey = key +" _dirty" ; 
        set(dirtyKey,"1") ; 
    }
    public void addDirtyBit(String key){
        String dirtyKey = key +" _dirty" ; 
        set(dirtyKey,"0") ; 
    }
    public void removeDirtyBit(String key){
        String dirtyKey = key +" _dirty" ; 
        delete(dirtyKey) ; 
    }
    public int readDirtyBit(String key){
        String dirtyKey = key +" _dirty" ; 
        return Integer.parseInt( (String)(get(dirtyKey)) ); 
    }

    @Override
    protected void finalize() throws Throwable{
        // save all the data in the redis to mysql
        save() ; 
    }

    public void addZSet(String key , String value , long score){
        try{
            
        redisTemplate.opsForZSet().add(key , value , score) ; 
    }catch(Exception e){
        System.err.println("Cant Connect to Redis , Use Storage Instead of Cache to Run");
        return ; 
    }
    }

    public Set<String> range(String key , long start , long end){
    try{
        // help to handle the end > the size 's problem
        return redisTemplate.opsForZSet().range(key , start , end>size(key)-1?size(key)-1:end) ; 
    }catch(Exception e){
        System.err.println("Cant Connect to Redis , Use Storage Instead of Cache to Run");
        return null; 
    }
    }

    public Set<String> rangeByLex(String key, Range<String> range){
        try{
            
        return redisTemplate.opsForZSet().rangeByLex(key , range) ; 
    }catch(Exception e){
        System.err.println("Cant Connect to Redis , Use Storage Instead of Cache to Run");
        return null; 
    }
    }
    public long size(String key) {
        try{
            
        return redisTemplate.opsForZSet().size(key) ; 
    }catch(Exception e){
        System.err.println("Cant Connect to Redis , Use Storage Instead of Cache to Run");
        return -1; 
    }
    }
}
