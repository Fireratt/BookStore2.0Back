package com.example.myapp.daoimpl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.myapp.dao.Tagdao;
import com.example.myapp.data.Tag;
import com.example.myapp.data.TagNode;
import com.example.myapp.repository.AccessTag;
import com.example.myapp.repository.AccessTagNode;
@Component
public class Tagdaoimpl implements Tagdao{
    @Autowired
    AccessTag accessTag ; 
    @Autowired
    AccessTagNode accessTagNode ; 
    public int putTag(int tagId , int bookId){
        if(accessTag.contains(tagId, bookId).size() != 0){  // already exist
            return 0 ; 
        }
        return accessTag.saveTagEntry(tagId , bookId) ;
    }

    
    public List<TagNode> getAll(){
        return accessTagNode.findAll() ;
    }

    public List<Tag> getContainIn2Edge(int tagId){
        List<TagNode> tags = accessTagNode.findByContainList(tagId) ; 
        Iterator<TagNode> tagIter = tags.iterator() ; 
        int [] tagIds = new int[tags.size()] ; 
        int cnt = 0 ; 
        while(tagIter.hasNext()){
            tagIds[cnt++] = tagIter.next().getId() ; 
        }
        return accessTag.findByHobbiesIn(tagIds) ; 
    }
}
