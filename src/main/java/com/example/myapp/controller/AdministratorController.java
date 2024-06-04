package com.example.myapp.controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import com.example.myapp.data.UserAuth;
import com.example.myapp.service.AdministratorService;
import com.example.myapp.utils.ByteUtils;
import com.example.myapp.utils.SessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import jakarta.servlet.http.* ; 
import com.example.myapp.service.AdministratorService.PermissionDeniedException;
import com.example.myapp.dto.*;
@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    AdministratorService administratorService ; 

    @GetMapping("/book")
    Book_dto[] searchBook(@RequestParam Map<String , String> params , HttpServletRequest request , HttpServletResponse response)
    {
        String name = params.get("bookname") ; 
        try {
            return administratorService.searchBook(name, request) ; 
        } catch (PermissionDeniedException e) {
            try
            {
                response.sendError(403) ; 
            }
            catch(Exception err)
            {
                System.out.println(err);
            }
        }
        return new Book_dto[]{} ; 

    }
    @PostMapping("/book")
    public Map<String,String> modifyBook(@RequestBody Map<String,Object> body , HttpServletRequest request , HttpServletResponse response)
    {
        HashMap<String,String> ret = new HashMap<>() ; 
        try{
            System.out.println("Size in Body:" + ByteUtils.getObjectSize(body) + "");
            String cover = null ; 
            if(body.get("cover") != null)
            {
                cover = body.get("cover").toString() ; 
                System.out.println(("ReceiveCover" + cover)) ; 
            }

            String name = body.get("name").toString() ; 
            String isbn = body.get("isbn").toString() ; 
            double price = Double.parseDouble( body.get("price").toString()) ; 
            String author = body.get("author").toString() ; 
            int storage = Integer.parseInt(body.get("storage").toString()) ; 
            int book_id = Integer.parseInt(body.get("book_id").toString()) ; 
            System.out.println("Input Modify,Storage:" + storage);
            if(administratorService.modifyBook(book_id , name , author,storage , cover ,isbn , price, request))
            {
                ret.put("state", "true") ; 
                return ret ; 
            }
            ret.put("state", "false") ; 
            return ret ; 
        }
        catch(PermissionDeniedException e)
        {
            try
            {
                System.out.println(e.message);
                response.sendError(403) ; 
                ret.put("state", "false") ; 
                return ret ; 
            }
            catch(Exception err)
            {
                System.out.println(err);
                ret.put("state", "false") ; 
                return ret ; 
            }
        }

    }

    @PutMapping("/book")
    public Map<String,String> addBook(@RequestBody Map<String,Object> body , HttpServletRequest request , HttpServletResponse response)
    {
        HashMap<String,String> ret = new HashMap<>() ; 
        try{
            String cover = null ; 
            if(body.get("cover") != null)
            {
                cover = body.get("cover").toString() ; 
            }
            String name = body.get("name").toString() ; 
            String author = body.get("author").toString() ; 
            int storage = Integer.parseInt(body.get("storage").toString()) ; 
            String description = body.get("description").toString() ; 
            String isbn =  body.get("isbn").toString() ; 
            Double price =Double.parseDouble(body.get("price").toString()) ; 
            if(administratorService.addBook(name , author , storage, price , isbn , description , cover , request))
            {
                ret.put("state", "true") ; 
                return ret ; 
            }
            ret.put("state", "false") ; 
            return ret ; 
        }
        catch(PermissionDeniedException e)
        {
            try
            {
                System.out.println(e.message);
                response.sendError(403) ; 
                ret.put("state", "false") ; 
                return ret ; 
            }
            catch(Exception err)
            {
                System.out.println(err);
                ret.put("state", "false") ; 
                return ret ; 
            }
        }
    }

    @DeleteMapping("/book")
    public Map<String,String> deleteBook(@RequestBody Map<String,Object> body , HttpServletRequest request , HttpServletResponse response)
    {
        HashMap<String,String> ret = new HashMap<>() ; 
        try{
            int book_id = Integer.parseInt(body.get("book_id").toString()) ; 
            if(administratorService.deleteBook(book_id, request))
            {
                ret.put("state", "true") ; 
                return ret ; 
            }
            ret.put("state", "false") ; 
            return ret ; 
        }
        catch(PermissionDeniedException e)
        {
            try
            {
                System.out.println(e.message);
                response.sendError(403) ; 
                ret.put("state", "false") ; 
                return ret ; 
            }
            catch(Exception err)
            {
                System.out.println(err);
                ret.put("state", "false") ; 
                return ret ; 
            }
        }
    }

}
