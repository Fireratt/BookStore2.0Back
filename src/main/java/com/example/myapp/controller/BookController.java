package com.example.myapp.controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.example.myapp.BookList;
import com.example.myapp.DemoApplication;
import com.example.myapp.DemoApplication.HttpException;
import com.example.myapp.dao.AccessAccount;
import com.example.myapp.dao.AccessBook;
import com.example.myapp.data.Book;
import com.example.myapp.data.UserAuth;
import com.example.myapp.dto.Book_Basic_dto;
import com.example.myapp.dto.Book_dto;
import com.example.myapp.service.BookService;
import com.example.myapp.utils.SessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.* ; 
@RestController
public class BookController {
    @Autowired 
    BookService accessBook ; 

    @GetMapping("/booklist")
    public Book_Basic_dto[] bookList(HttpServletRequest request)
    {
        System.out.println(SessionUtils.readSession("user_id", request)) ; 
        return accessBook.getList(request) ; 
    }

    @GetMapping("/book")
	public Book_dto bookInfo(@RequestParam Map<String,String> book , HttpServletRequest request)
	{
		// String bookName = book.get("Name") ; 
        int bookId = Integer.parseInt(book.get("id")) ; 
		System.out.println(book.get("id")) ; 
		// booklist save all the book's information . And call the find method to get it according the name 
        Book_dto ret = accessBook.get(bookId , request) ; 
		if(ret == null)
		{
			System.out.println("ERROR , NULL NAME!") ; 
		    throw new DemoApplication.HttpException() ; 
		} 
		// insert the book to the database when develop
		// Book ret = BookList.find(bookName); 
		// accessBook.addBook(ret);
		System.out.println("SUCCESS") ; 
		return ret ; 

	}
	
}

