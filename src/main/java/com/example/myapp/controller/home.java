package com.example.myapp.controller ; 
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.* ; 			// the jackson
import com.fasterxml.jackson.core.JsonProcessingException ; 
import java.util.*;
import com.example.myapp.*;
import org.springframework.http.* ; 
@RestController
public class home {
    @RequestMapping("/Home")
	String home(){
		return "HELLO WORLD!" ;   

	}
    
}