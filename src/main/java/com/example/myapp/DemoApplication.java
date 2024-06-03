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
import com.example.myapp.data.Order;

import org.springframework.http.HttpHeaders ; 
import com.example.myapp.FilterRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import com.example.myapp.controller.*;
// @CrossOrigin
@RestController
@SpringBootApplication
public class DemoApplication {
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "bad request")
	public static class HttpException extends RuntimeException
	{

	}
	// handle pay request
	// @PostMapping("/pay")
	// // public ResponseEntity<Order> buyController(@RequestBody Map<String,String> order)
	// public Order buyController(@RequestBody Map<String,String> order)
	// {
	// 	// headers.add("Access-Control-Allow-Credentials" , "true") ;
	// 	// headers.add("Access-Control-Allow-Origin" , "http://localhost:3000") ;  
	// 	// headers.add("Access-Control-Allow-Headers","*") ; 
	// 	// headers.add("Access-Control-Allow-Methods","*") ; 
	// 	// headers.add("Access-Control-Expose-Headers" , "*") ; 
	// 	// headers.add("Access-Control-Max-Age","3600") ; 
	// 	// headers.add("CONTENT-Type","application/json") ; 

	// 	String bookName = order.get("Name") ; 
	// 	double bookPaid = Double.parseDouble( order.get("Paid")) ; 
	// 	double bookPrice =  Double.parseDouble( order.get("Price")) ; 
	// 	int orderCode = Integer.parseInt(order.get("Code"))  ; 
	// 	String orderDate = order.get("Date") ; 
	// 	int orderStatus = 0 ; 
	// 	int amount = Integer.parseInt(order.get("Amount")) ; 
	// 	// output the order infomation by serializing
	// 	ObjectMapper mapper = new ObjectMapper(); 
	// 	String jsonResult = ""; 
	// 	try{
	// 			jsonResult = mapper.writerWithDefaultPrettyPrinter()
	// 	.writeValueAsString(order);
	// 	}
	// 	catch(JsonProcessingException err )
	// 	{
	// 		System.out.println(err) ; 
	// 	}
	// 	System.out.println(jsonResult) ; 
	// 	final Order ret = new Order(bookName ,bookPrice, bookPaid,orderStatus,orderCode,orderDate,amount) ; 
	// 	return ret ; 
	// }
	// handle pay item information query 



	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	} 

}
