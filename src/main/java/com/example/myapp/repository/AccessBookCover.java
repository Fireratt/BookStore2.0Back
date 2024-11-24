package com.example.myapp.repository; 
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.example.myapp.data.BookCover;

public interface AccessBookCover extends MongoRepository<BookCover, Integer> {
    
    List<BookCover> findByIdIn(List<Integer> bookids) ; 
}
