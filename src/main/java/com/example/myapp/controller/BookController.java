package com.example.myapp.controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.example.myapp.BookList;
import com.example.myapp.DemoApplication;
import com.example.myapp.DemoApplication.HttpException;
import com.example.myapp.dao.AccessAccount;
import com.example.myapp.dao.AccessBook;
import com.example.myapp.data.Book;
import com.example.myapp.data.Book_Basic;
import com.example.myapp.data.UserAuth;
import com.example.myapp.utils.SessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.* ; 
@RestController
public class BookController {
    @Autowired 
    AccessBook accessBook ; 

    @GetMapping("/booklist")
    public Book_Basic[] bookList(HttpServletRequest request)
    {
        System.out.println(SessionUtils.readSession("user_id", request)) ; 
        return accessBook.getBookList(1) ; 
    }

    @GetMapping("/book")
	public Book bookInfo(@RequestParam Map<String,String> book)
	{
        String bookId = book.get("id") ; 
		System.out.println(book.get("id")) ; 
		// booklist save all the book's information . And call the find method to get it according the name 
        Book ret = accessBook.getBookInfo(bookId) ; 
		if(ret == null)
		{
			System.out.println("ERROR , NULL NAME!") ; 
		    throw new DemoApplication.HttpException() ; 
		} 
		// insert the book to the database when develop
		// accessBook.addBook(ret);
		System.out.println("SUCCESS") ; 
		return ret ; 

	}
}

