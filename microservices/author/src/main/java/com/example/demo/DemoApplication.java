package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@RestController
public class DemoApplication {

	@Autowired
	// we use jdbc because this is a microservice . 
	// use jpa is too large as we need to add the Data Class and do many other works . 
	JdbcTemplate jdbcTemplate ; 
	@Autowired
	RedisTemplate redisTemplate ; 
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@GetMapping("/{bookname}")
	public String GetAuthor(@PathVariable("bookname") String bookName){
		
		return bookName; 
	}
}
