package com.example.myapp.controller ; 
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.example.myapp.dao.AccessAccount;
import com.example.myapp.data.UserAuth;
import com.example.myapp.utils.SessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.* ; 
@RestController
public class AccountController {
    @Autowired
    private AccessAccount accessAccount ; 
	@PostMapping("/login")
	public Map<String,String> confirmLogin(@RequestBody Map<String,String> Account ,  HttpServletRequest request , HttpServletResponse response)
	{
		String userName = Account.get("username") ;
		String password = Account.get("password") ;  
		System.out.println("Get Request:" + userName+ ":" + password);
		HashMap<String,String> ret = new HashMap<>() ; 
		String user_id = accessAccount.confirmLogin(userName, password); 
		System.out.println("Result" + user_id);
        if(user_id != null)
		{
			ret.put("State", "Success") ; 
			SessionUtils.setSession(new UserAuth(user_id), request);
			return ret ; 
		}
		else
		{
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

		accessAccount.register(userName,mail);
		int userId = accessAccount.getNewUserId() ; 
		accessAccount.saveAuth(userId, password);
		HashMap<String,String> ret = new HashMap<>() ; 
		ret.put("State", "Success") ; 
		return ret ; 
	}


}