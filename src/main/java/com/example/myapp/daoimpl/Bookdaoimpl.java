package com.example.myapp.daoimpl; 

import org.springframework.stereotype.* ;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.jdbc.core.JdbcTemplate ; 
import com.example.myapp.BookList;
import com.example.myapp.dao.Bookdao;
import com.example.myapp.data.Book;
import com.example.myapp.dto.BookRank;
import com.example.myapp.repository.AccessBook;
import com.example.myapp.utils.PageUtils;
import com.example.myapp.utils.RedisWrapper;
import com.example.myapp.utils.RedisWrapper.ZScanResult;

import org.springframework.data.redis.connection.Limit ; 
import org.springframework.data.redis.core.RedisTemplate ; 
import com.alibaba.fastjson2.JSON ; 
@Repository

public class Bookdaoimpl implements Bookdao{

    @Autowired
    private AccessBook accessBook ; 

    @Autowired
    private RedisWrapper redisTemplate ; 
    final String ZSetKey = "BookCache" ; 
    final String ZSetNameKey = "BookName" ; 
    // this function check if the book zset have been set . If not , set it ; or do nothing . 
    public Bookdaoimpl(){
        // we will cache all the books in the redis first to improve the efficiency
        initializeBookZSet();
    }
    private void initializeBookZSet(){
        if(redisTemplate.size(ZSetKey) == 0){
            // cache all the book 
            List<Book> booklist = accessBook.findall() ; 
            Iterator<Book> iter = booklist.iterator() ; 
            while(iter.hasNext()){
                Book book = iter.next() ; 
                if(redisTemplate.get("book"+book.getBookId()) == null){
                    redisTemplate.set("book"+book.getBookId(), JSON.toJSONString(book));
                }
                redisTemplate.addZSet(ZSetKey, "" + book.getBookId(), book.getBookId());
                redisTemplate.addZSet(ZSetNameKey, book.getName(), book.getBookId());
            }
        }
    }
    public Page<Book> findByPage(Pageable pageStatus) 
    {
        initializeBookZSet();
        String[] list = redisTemplate.range(ZSetKey, pageStatus.getPageNumber() * pageStatus.getPageSize()
        , (pageStatus.getPageNumber() + 1) * pageStatus.getPageSize() -1).toArray(new String[0]) ; 
        Book[] booklist = new Book[list.length] ; 
        int cnt = 0 ; 
        for (String string : list) {
            String bookJSON = (String)redisTemplate.get("book" + string) ; 
            booklist[cnt] = (Book)JSON.parseObject(bookJSON , Book.class) ; 
            cnt ++ ; 
        }
        System.out.println("<BookDaoImpl>Find Total Element Number:" + redisTemplate.size(ZSetKey));
        return PageUtils.toPage(booklist, pageStatus, redisTemplate.size(ZSetKey)) ; 
    }

    public Book findByBookId(int Book_Id)
    {
        String bookString = (String)redisTemplate.get("book" + Book_Id) ; 
        if(bookString!=null){
            System.out.println("Book " +Book_Id + "is in the redis");
            return JSON.parseObject(bookString , Book.class) ; 
        }
        // not find
        System.out.println("Book " +Book_Id + "not in the redis");
        System.out.println("Search Book " +Book_Id + "in the DB");
        Book book = accessBook.findByBookId(Book_Id) ; 
        System.out.println("Set Book " +Book_Id + "in the redis");
        redisTemplate.set("book"+Book_Id , JSON.toJSONString(book)) ; 
        return book ; 
    }

    public Book save(Book result)
    {
        return accessBook.save(result) ; 
    }

    public Page<Book> SearchByName(String name, Pageable pageStatus)
    {
        initializeBookZSet();
        // create a regex for the name to be recognized 
        String regex = "*" + name + "*" ; 
        Range<String> range = Range.closed(regex, regex) ; 
        ZScanResult result = redisTemplate.ZScanWithScore(ZSetNameKey, regex
        , pageStatus.getPageSize(), pageStatus.getPageNumber() * pageStatus.getPageSize()) ; 
        System.out.println("<BookDaoImpl SearchByName> Find Total Element Number:" +result.getMatchedElements());
        // String[] list = redisTemplate.rangeByLex(ZSetNameKey,range , pageStatus.getPageNumber() * pageStatus.getPageSize()
        // , pageStatus.getPageSize()).toArray(new String[0]) ; 
        Double[] list = result.getList().toArray(new Double[0]) ; 
        Book[] booklist = new Book[list.length] ; 
        int cnt = 0 ; 
        for (Double bookid : list) {
            System.out.println("<BookDaoImpl SearchByName> receive Integer:" + bookid.intValue());
            String bookJSON = (String)redisTemplate.get("book" + bookid.intValue()) ; 
            booklist[cnt] = (Book)JSON.parseObject(bookJSON , Book.class) ; 
            cnt ++ ; 
        }

        return PageUtils.toPage(booklist, pageStatus, result.getMatchedElements()) ; 
//        return accessBook.SearchByName(name , pageStatus) ; 
    }

    public int modifyBook(int book_id , String name , String author , int Storage ,  String isbn , double price ) 
    {
        // need to modify the cache in the redis
        String bookString = (String)redisTemplate.get("book" + book_id) ; 
        if(bookString!=null){
            System.out.println("Book " +book_id + "is in the redis and need to update");
            Book book = (Book)JSON.parseObject(bookString , Book.class) ; 
            book.setName(name);
            book.setAuthor(author);
            book.setStorage(Storage);
            book.setIsbn(isbn);
            book.setPrice(new BigDecimal(price));
            redisTemplate.setDirty("book"+book_id);
            redisTemplate.set("book"+book_id , JSON.toJSONString(book)) ; 
            return 1 ; 
        }
        // not find
        int result = accessBook.modifyBook(book_id, name, author, Storage , isbn , price) ; 
        Book toInsert = findByBookId(book_id) ; 
        System.out.println("Book " +book_id + "not in the redis , insert in redis use find when modify");
        return result ; 
    }
    
    public int modifyCover(int book_id , String cover)
    {
        // need to modify the cache in the redis
        String bookString = (String)redisTemplate.get("book" + book_id) ; 
        if(bookString!=null){
            System.out.println("Book " +book_id + "is in the redis and need to update");
            Book book = (Book)JSON.parseObject(bookString , Book.class) ; 
            book.setCover(cover);
            redisTemplate.setDirty("book"+book_id);
            redisTemplate.set("book"+book_id , JSON.toJSONString(book)) ; 
            return 1; 
        }
                // not find need to modify directly in the db , and extract out from the db to the redis 
        int result = accessBook.modifyCover(book_id, cover) ; 
        Book toInsert = findByBookId(book_id) ; 
        System.out.println("Book " +book_id + "not in the redis , insert in redis use find when modify");
        return result ; 
    }

    public int addBook(String name ,double price ,String author, String description , int storage , String isbn , String cover) 
    {
        // use writethrough strategy
        Book book = accessBook.save(new Book(name, price, author, description, storage, isbn, cover)) ; 
        // insert it in the redis
        System.out.println("Book " +book.getBookId()+ "insert in redis when addbook");
        redisTemplate.set("book"+book.getBookId(), JSON.toJSONString(book));
        return 1 ; 
    }

    public int deleteBook(int book_id)
    {
        // need to modify the cache in the redis  use writethrough strategy
        boolean result = redisTemplate.delete("book"+book_id) ;
        if(result){
            System.out.println("Book " +book_id + "in the redis and be deleted");
        }else{
            System.out.println("Book " +book_id + "not in the redis and no need to be deleted");
        }
        return accessBook.deleteBook(book_id) ; 
    }

    public Integer checkStorage(int book_id , int number)
    {
        // read from cache
        String bookString = (String)redisTemplate.get("book" + book_id) ; 
        if(bookString!=null){
            System.out.println("Book " +book_id + "is in the redis and need to update");
            Book book = (Book)JSON.parseObject(bookString , Book.class) ; 
            return book.getStorage() > number? 1 : null ; 
        }
            // not find
        System.out.println("Book " +book_id + "not in the redis , no need to update");
        return accessBook.checkStorage(book_id , number) ; 
    }

    public int updateStorage(int book_id , int number)
    {
        // need to modify the cache in the redis
        // wait until automatically save to mysql
        String bookString = (String)redisTemplate.get("book" + book_id) ; 
        if(bookString!=null){
            System.out.println("Book " +book_id + "is in the redis and need to update");
            Book book = (Book)JSON.parseObject(bookString , Book.class) ; 
            book.setStorage(book.getStorage() - number);
            // set the dirty bit to 1 
            redisTemplate.setDirty("book"+book_id);
            redisTemplate.set("book"+book_id , JSON.toJSONString(book)) ; 
            return 1 ; 
        }
            // not find
        System.out.println("Book " +book_id + "not in the redis , no need to update");
        return accessBook.updateStorage(book_id, number) ; 
    }

    public List<Map> getBookRank(String start , String end,Pageable pageStatus)
    {
        return accessBook.getBookRank(start , end ) ; 
    }

}