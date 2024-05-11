package com.example.myapp.controller ; 
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.* ; 			// the jackson
import com.fasterxml.jackson.core.JsonProcessingException ; 
import java.util.*;
import com.example.myapp.*;
import com.example.myapp.dao.AccessAccount;
import com.example.myapp.dao.AccessUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import org.springframework.http.* ; 
@RestController
public class AccountController {
    @autoWired
    private AccessAccount accessAccount ; 
	@PostMapping("/login")
	public String confirmLogin(@RequestBody Map<String,String> Account)
	{
		String userName = Account.get("username") ;
		String password = Account.get("password") ;  
        accessAccount.confirmLogin(userName, password);
	}

}