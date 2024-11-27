package com.example.myapp.dao;
import java.util.*;

import org.springframework.stereotype.Component;

import com.example.myapp.data.Tag;
import com.example.myapp.data.TagNode;
@Component
public interface Tagdao {
    int putTag(int tagId , int bookId) ; 

    List<TagNode> getAll() ; 

    List<Tag> getContainIn2Edge(int tagId) ; 
}
