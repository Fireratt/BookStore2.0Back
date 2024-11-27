package com.example.myapp.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.dao.Tagdao;
import com.example.myapp.data.Tag;
import com.example.myapp.data.TagNode;
import com.example.myapp.service.TagService;
@Service
public class TagServiceimpl implements TagService{
    @Autowired
    Tagdao accessTag ; 
    public boolean putTag(int tagId , int bookId) {
        try{
            if(accessTag.putTag(tagId, bookId)==0){
                return false ; 
            }
        }
        catch(Exception e){
            return false ; 
        }
        return true ; 
    }
    public List<TagNode> getAll(){
        return accessTag.getAll() ; 
    }

    public List<Tag> getContainsIn2(int tagId){
        return accessTag.getContainIn2Edge(tagId) ; 
    }
}
