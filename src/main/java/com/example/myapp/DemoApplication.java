package com.example.myapp;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.* ; 
// import org.springframework.web.bind.annotation.GetMapping ; 
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.autoconfigure.SpringBootApplication ; 
import com.fasterxml.jackson.annotation.JsonIgnore ; 
import org.springframework.boot.SpringApplication ; 
import com.example.myapp.BookList;
import java.util.*;

@RestController
@SpringBootApplication
public class DemoApplication {

	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "bad request")
	public class HttpException extends RuntimeException
	{

	}
	@RequestMapping("/Home")
	String home(){
		return "HELLO WORLD!" ;   
	}
	@GetMapping("/Book")
	public Book bookController(@RequestParam Map<String,String> book)
	{
		System.out.println(book.get("Name")) ; 
		// booklist save all the book's information . And call the find method to get it according the name 
		System.out.println(BookList.find(book.get("Name"))) ; 
		Book ret = BookList.find(book.get("Name")) ; 
		if(ret == null)
		{
		 throw new HttpException() ; 
		}

		return ret ; 
	}

	// when cant find the book

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	} 

}
