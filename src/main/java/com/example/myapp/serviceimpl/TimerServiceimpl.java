package com.example.myapp.serviceimpl;
import com.example.myapp.service.TimerService;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.annotation.SessionScope;

@org.springframework.stereotype.Service
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TimerServiceimpl implements TimerService , Serializable{
    Date startTime ; 
    public void login() {
        startTime = new Date() ; 
    }
    public long logout(){
        if(startTime == null){
            return -1 ; 
        }
        return new Date().getTime() - startTime.getTime() ; 
    }
    
}
