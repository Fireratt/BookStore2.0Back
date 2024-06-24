package com.example.myapp.controller ; 
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

import com.example.myapp.dao.Accountdao;
import com.example.myapp.data.UserAuth;
import com.example.myapp.utils.SessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import jakarta.servlet.http.* ; 
@RestController
public class AccountController {
    @Autowired
    private Accountdao accessAccount ; 
	@PostMapping("/login")
	public Map<String,String> confirmLogin(@RequestBody Map<String,String> Account ,  HttpServletRequest request , HttpServletResponse response)
	{
		String userName = Account.get("username") ;
		String password = Account.get("password") ;  
		System.out.println("Get Request:" + userName+ ":" + password);
		HashMap<String,String> ret = new HashMap<>() ; 
		try
		{
			List<Map> user = accessAccount.confirmLogin(userName, password); 
			System.out.println("Result" + user.get(0).get("user_id"));
			if(user.size() != 0)
			{
				System.out.println(user.get(0).get("ban").toString());
				if(Boolean.parseBoolean(user.get(0).get("ban").toString())) 
				{
					ret.put("State", "Rejected") ; 
					return ret ; 
				}
				ret.put("State", "Success") ; 
				ret.put("Administrator" , user.get(0).get("administrator").toString()) ; 
				SessionUtils.setSession(new UserAuth(user.get(0).get("user_id").toString()), request);
				return ret ; 
			}
			else
			{
				ret.put("State", "Failed") ; 
				return ret ; 
			}
		}
		catch(Exception err)
		{
			err.printStackTrace();
			ret.put("State", "Failed") ; 
			return ret ; 
		}

	}
	@PutMapping("/register")
	public Map<String,String> register(@RequestBody Map<String,String> newAccount)
	{
		String userName = newAccount.get("username") ;
		String password = newAccount.get("password") ;  
		String mail = newAccount.get("mail") ;  
		HashMap<String,String> ret = new HashMap<>() ; 
		try
		{
			
			accessAccount.register(userName,mail);
			int userId = accessAccount.getNewUserId() ; 
			accessAccount.saveAuth(userId, password);
			ret.put("State", "Success") ; 
			return ret ; 
		}
		catch (DataIntegrityViolationException exp)
		{
			System.err.println("Catch SQL Integrity Erro");
			ret.put("State", "Duplicate") ; 
			return ret ; 
		}
	}


}