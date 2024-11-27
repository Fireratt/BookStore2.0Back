package com.example.myapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.myapp.data.Tag;
import com.example.myapp.data.TagNode;
@Service
public interface TagService {
    public boolean putTag(int tagId , int bookId) ; 

    public List<TagNode> getAll() ; 

    public List<Tag> getContainsIn2(int tagId) ; 
}
