package com.example.myapp.controller;

import java.util.HashMap;
import java.util.*;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.data.Tag;
import com.example.myapp.data.TagNode;
import com.example.myapp.dto.Book_Basic_dto;
import com.example.myapp.service.TagService;

import jakarta.servlet.http.* ; 
@RestController
public class TagController {
    @Autowired
    TagService accessTag ; 
    /*
	 * body.tagId , body.bookId
	 * 
	 */
	@PostMapping("/tag")
	public Map<String,String> addTag(@RequestBody Map<String,Object> body , HttpServletRequest request , HttpServletResponse response){
		HashMap<String,String> ret = new HashMap<>();
		int tagId = Integer.parseInt(body.get("tagId").toString() );
		int bookId = Integer.parseInt(body.get("bookId").toString()) ; 
		if(accessTag.putTag(tagId, bookId)){
			ret.put("result", "ok") ; 
		}else{
			ret.put("result", "fail") ; 
		}
		return ret ;
	}

	/*
	 * get all tag from the neo4j
	 */
	@GetMapping("/tag")
	public TagNode[] getTag(){
		return accessTag.getAll().toArray(new TagNode[0]) ; 
	}

    @GetMapping("/tag/{id}")
    public Book_Basic_dto[] getTagInContain2(@PathVariable(name = "id") int id){
        List<Tag> tags = accessTag.getContainsIn2(id) ; 
        HashMap<Integer , Book_Basic_dto> retMap = new HashMap<>() ; 
        Iterator<Tag> tagIter = tags.iterator() ; 
        while(tagIter.hasNext()){
            Tag current = tagIter.next() ; 
            if(retMap.containsKey(current.getTagid())){
                continue ;
            }
            retMap.put(current.getTagid(), current.getBook().toBasicDto()) ; 
        }
        return retMap.values().toArray(new Book_Basic_dto[0]) ; 
    }
}
