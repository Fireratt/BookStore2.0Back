package com.example.myapp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping ; 
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.autoconfigure.SpringBootApplication ; 
import com.fasterxml.jackson.annotation.JsonIgnore ; 
import org.springframework.boot.SpringApplication ; 
import java.util.*;
class Book
{
	public String Name ; 
	public String Author ; 
	public String Description ; 
	@JsonIgnore
	private Double Price ; 
	public void setName(String name)
	{
		Name = name ; 
	}
	public void setAuthor(String iAuthor)
	{
		Author = iAuthor ; 
	}	
	public void setDescription(String iDescr)
	{
		Description = iDescr ; 
	}	
	public void setPrice(double iPrice)
	{
		Price = iPrice ; 
	}
	public Book()
	{

	}
}
@RestController
@SpringBootApplication
public class DemoApplication {

	@RequestMapping("/Home")
	String home(){
		return "HELLO WORLD!" ;   
	}
	@GetMapping("/Book")
	public Book bookController(@RequestParam Map<String,String> book)
	{
		System.out.println(book.get("Name")) ; 
		Book retBook = new Book()  ; 
		retBook.setName("OOO")  ; 
		retBook.setAuthor("LIU") ; 
		retBook.setPrice(300) ; 
		retBook.setDescription("AO!")  ;
		return retBook ; 
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
