package com.example.myapp;

import org.springframework.web.bind.annotation.*;

import org.springframework.http.* ; 
import org.springframework.boot.autoconfigure.SpringBootApplication ; 
import com.fasterxml.jackson.annotation.JsonIgnore ; 
import org.springframework.boot.SpringApplication ;
import com.fasterxml.jackson.databind.* ; 			// the jackson
import com.fasterxml.jackson.core.JsonProcessingException ; 
import java.util.*;
import com.example.myapp.*;
import com.example.myapp.data.Book;
import org.springframework.http.HttpHeaders ; 
import com.example.myapp.FilterRegistration;
import com.example.myapp.dao.AccessBook; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
// @CrossOrigin
@RestController
@SpringBootApplication
public class DemoApplication {
	@Autowired
		private AccessBook accessBook ;
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "bad request")
	public class HttpException extends RuntimeException
	{

	}
	@RequestMapping("/Home")
	String home(){
		return "HELLO WORLD!" ;   

	}
	// @CrossOrigin
	@GetMapping("/Book")
	public Book bookController(@RequestParam Map<String,String> book)
	{
		System.out.println(book.get("Name")) ; 
		System.out.println(book.get("id")) ; 
		// booklist save all the book's information . And call the find method to get it according the name 
		System.out.println(BookList.find(book.get("Name"))) ; 
		Book ret = BookList.find(book.get("Name")) ; 
		if(ret == null)
		{
			System.out.println("ERROR , NULL NAME!") ; 
		 throw new HttpException() ; 
		} 
		// insert the book to the database when develop
		// accessBook.addBook(ret);
		System.out.println("SUCCESS") ; 
		return ret ; 

	}
	@GetMapping("/order")
	// public ResponseEntity<Order[]> orderController()
	public Order[] orderController()
	{
		return OrderList.Orders ; 
	}
	// handle pay request
	@PostMapping("/pay")
	// public ResponseEntity<Order> buyController(@RequestBody Map<String,String> order)
	public Order buyController(@RequestBody Map<String,String> order)
	{
		// headers.add("Access-Control-Allow-Credentials" , "true") ;
		// headers.add("Access-Control-Allow-Origin" , "http://localhost:3000") ;  
		// headers.add("Access-Control-Allow-Headers","*") ; 
		// headers.add("Access-Control-Allow-Methods","*") ; 
		// headers.add("Access-Control-Expose-Headers" , "*") ; 
		// headers.add("Access-Control-Max-Age","3600") ; 
		// headers.add("CONTENT-Type","application/json") ; 

		String bookName = order.get("Name") ; 
		double bookPaid = Double.parseDouble( order.get("Paid")) ; 
		double bookPrice =  Double.parseDouble( order.get("Price")) ; 
		int orderCode = Integer.parseInt(order.get("Code"))  ; 
		String orderDate = order.get("Date") ; 
		int orderStatus = 0 ; 
		int amount = Integer.parseInt(order.get("Amount")) ; 
		// output the order infomation by serializing
		ObjectMapper mapper = new ObjectMapper(); 
		String jsonResult = ""; 
		try{
				jsonResult = mapper.writerWithDefaultPrettyPrinter()
		.writeValueAsString(order);
		}
		catch(JsonProcessingException err )
		{
			System.out.println(err) ; 
		}
		System.out.println(jsonResult) ; 
		final Order ret = new Order(bookName ,bookPrice, bookPaid,orderStatus,orderCode,orderDate,amount) ; 
		return ret ; 
	}
	// handle pay item information query 
	@GetMapping("/pay")
	public Map<String,String> queryController(@RequestParam Map<String,String> request)
	{
		String bookName = request.get("Name") ; 
		String BookAmount = request.get("Amount") ; 
		System.out.println(request.get("Name")) ; 
		Book bookInfo = BookList.find(bookName) ; 
		HashMap<String,String> ret = new HashMap<String,String>() ; 
		ret.put("Name" ,"" +  bookInfo.Name) ; 
		ret.put("Code" , "1") ; 
		ret.put("Paid" , "" +bookInfo.Real_Price) ; 
		ret.put("Price" ,"" + bookInfo.Price) ; 
		ret.put("Date" , "1970/1/1") ; 
		ret.put("Amount" , BookAmount) ; 
		return ret ; 
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	} 

}
