package com.example.myapp.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.myapp.data.Book;
import com.example.myapp.data.Tag;

import jakarta.transaction.Transactional;
import java.util.*;
@Repository
public interface AccessTag extends JpaRepository<Tag , Integer>{
    @Query("SELECT p FROM Tag p WHERE p.tagid IN ?1")  
    List<Tag> findByHobbiesIn(int[] ids);  
    
    @Transactional
    @Modifying
    @Query(value = "Insert into tag(tagid , book_id) values(?1 , ?2)" , nativeQuery = true)
    int saveTagEntry(int tagId , int bookId) ;

    @Transactional
    @Query(value = "Select p From Tag p Where p.tagid = ?1 and p.book_id = ?2")
    List<Tag> contains(int tagId , int bookId) ; 
}
