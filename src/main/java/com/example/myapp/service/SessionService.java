package com.example.myapp.service;
import com.example.myapp.data.Cart;
import com.example.myapp.repository.AccessUser;
import com.example.myapp.utils.SessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.* ; 
@Service
public class SessionService {
    AccessUser accessUser ; 
    public static int getUserId(HttpServletRequest request)
    {
        return Integer.parseInt(SessionUtils.readSession("user_id", request)) ; 
    }
}
