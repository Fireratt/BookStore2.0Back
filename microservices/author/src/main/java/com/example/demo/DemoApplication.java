package com.example.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson2.JSON ;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@RestController
public class DemoApplication {

	@Autowired
	// I use jdbc because this is a microservice . 
	// I want to avoid complicated classes definitions and function calls in jpa
	JdbcTemplate jdbcTemplate ; 
	@Autowired
	RedisTemplate redisTemplate ; 
	final String ZSetKey = "BookCache" ; 
    final String ZSetNameKey = "BookName" ; 
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@GetMapping("/{bookname}")
	public String GetAuthor(@PathVariable("bookname") String bookName){
		// if the book have been cached , it will have been added to ZSet
		System.out.println("<Author-Service> Enter the Function");
		Double bookId = redisTemplate.opsForZSet().score(ZSetNameKey,bookName) ; 
		try{
			if(bookId != null){
				Book book = (Book)JSON.parseObject((String)(redisTemplate.opsForValue().get(bookId.intValue())) , Book.class); 
				return book.getAuthor() ;
			}
		}
		catch(Exception e){
			// do nothing , just through
		}
		try{
			Map<String , Object> result = jdbcTemplate.queryForMap("Select b.author from book b where b.name = ?" , bookName) ; 
			return (String)result.get("author") ; 
		}
		catch(Exception e){
			e.printStackTrace();
			return "Provided A False Author" ; 
		}
	}
}
