package com.example.myapp.controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.example.myapp.BookList;
import com.example.myapp.DemoApplication;
import com.example.myapp.DemoApplication.HttpException;
import com.example.myapp.data.Book;
import com.example.myapp.data.UserAuth;
import com.example.myapp.dto.Book_Basic_dto;
import com.example.myapp.dto.Book_dto;
import com.example.myapp.service.BookService;
import com.example.myapp.utils.PageUtils;
import com.example.myapp.utils.SessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable; 
import org.springframework.data.domain.Page; 

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import jakarta.servlet.http.* ; 
@RestController
public class BookController {
    @Autowired 
    BookService accessBook ; 
	static final int PAGE_SIZE = 3 ; 
    @GetMapping("/booklist")
    public Page<Book_Basic_dto> bookList(@RequestParam Map<String,String> page , HttpServletRequest request)
    {
		int pageNumber =Integer.parseInt(page.get("page")) ; 
        System.out.println(SessionUtils.readSession("user_id", request)) ; 
		Pageable pageStatus = PageRequest.of(pageNumber, PAGE_SIZE) ; 
		// Book_Basic_dto[] result = accessBook.getList(pageStatus , request) ;
        // return PageUtils.toPage(result , pageStatus); 
		return accessBook.getList(pageStatus , request) ; 
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
	@GetMapping("/book/search")
	public Page<Book_Basic_dto> searchBook(@RequestParam Map<String,String> param , HttpServletRequest request)
	{
        String name = param.get("bookname") ; 
		int page = Integer.parseInt( param.get("page")) ; 
		Pageable pageStatus = PageRequest.of(page, PAGE_SIZE) ; 

        return accessBook.searchBook(name,pageStatus, request) ; 
	}

}

