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
import com.example.myapp.data.BookCover;
import com.example.myapp.dto.BookRank;
import com.example.myapp.repository.AccessBook;
import com.example.myapp.repository.AccessBookCover;
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

    @Autowired
    private AccessBookCover accessCover ; 


    final String ZSetKey = "BookCache" ; 
    final String ZSetNameKey = "BookName" ; 
    // this function check if the book zset have been set . If not , set it ; or do nothing . 
    public Bookdaoimpl(){
        // we will cache all the books in the redis first to improve the efficiency
    }
    private boolean initializeBookZSet(){
        if(redisTemplate.size(ZSetKey)== -1){   // redis dont connected 
            // only do once . we load all data from mysql to the mongo db 
            // List<Book> booklist = accessBook.findAll() ; 
            // ArrayList<BookCover> bookCovers = new ArrayList<>(booklist.size()) ; 
            // Iterator<Book> iter = booklist.iterator() ; 
            // while(iter.hasNext()){
            //     Book current = iter.next() ; 
            //     bookCovers.add(new BookCover(current.getBookId() , current.getCover())) ; 
            // }
            // accessCover.insert(bookCovers) ; 
            // System.out.println("<BookDaoImpl> Write The Data From Mysql To Mongo End");
            return false ; 
        }
        if(redisTemplate.size(ZSetKey) == 0 ){
            // cache all the book 
            System.out.println("<BookDao InitializeBookZSet>"+redisTemplate.size(ZSetKey));
            List<Book> booklist = accessBook.findall() ; 
            List<BookCover> coverList = accessCover.findAll() ; 
            Iterator<Book> iter = booklist.iterator() ; 
            Iterator<BookCover> coverIter = coverList.iterator() ; 
            while(iter.hasNext()){
                Book book = iter.next() ; 
                BookCover cover ;
                if(coverIter.hasNext()){
                    cover = coverIter.next()  ; 
                }
                else{
                    cover = new BookCover() ; 
                }
                book.setCover(cover.getBase64());
                if(redisTemplate.get("book"+book.getBookId()) == null){
                    redisTemplate.set("book"+book.getBookId(), JSON.toJSONString(book));
                }
                redisTemplate.addZSet(ZSetKey, "" + book.getBookId(), book.getBookId());
                redisTemplate.addZSet(ZSetNameKey, book.getName(), book.getBookId());
            }
        }
        return true ; 
    }
    public Page<Book> findByPage(Pageable pageStatus) 
    {
        if(!initializeBookZSet()){  // redis dont connected
            Page<BookCover> pageCover = accessCover.findAll(pageStatus) ; 
            Page<Book> pageBook = accessBook.findByPage(pageStatus) ; 

            List<BookCover> covers = pageCover.toList() ; 
            int cnt = 0 ;
            Iterator<BookCover> coverIter = covers.iterator() ; 
            while(coverIter.hasNext()){
                try{
                    pageBook.getContent().get(cnt++).setCover(coverIter.next().getBase64());
                }
                catch(IndexOutOfBoundsException e){
                    System.out.println("<BookDaoImpl> The Index > pageBook's Content Size");
                }
            }
            return pageBook ; 
        }
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
        initializeBookZSet();
        String bookString = (String)redisTemplate.get("book" + Book_Id) ; 
        if(bookString!=null){
            System.out.println("Book " +Book_Id + "is in the redis");
            return JSON.parseObject(bookString , Book.class) ; 
        }
        // not find
        System.out.println("Book " +Book_Id + "not in the redis");
        System.out.println("Search Book " +Book_Id + "in the DB");
        Book book = accessBook.findByBookId(Book_Id) ; 
        SetCover(book) ; 
        System.out.println("Set Book " +Book_Id + "in the redis");
        redisTemplate.set("book"+Book_Id , JSON.toJSONString(book)) ; 
        return book ; 
    }

    public Book save(Book result)
    {
        // save in mongo
        accessCover.save(new BookCover(result.getBookId(), result.getCover())) ; 
        return accessBook.save(result) ; 
    }

    public Page<Book> SearchByName(String name, Pageable pageStatus)
    {
        if(!initializeBookZSet()){  // redis dont connected

            Page<Book> result = accessBook.SearchByName(name, pageStatus) ; 
            SetCover(result);
            return result ; 
        }        // create a regex for the name to be recognized 
        String regex = "*" + name + "*" ; 
        // Range<String> range = Range.closed(regex, regex) ; 
        ZScanResult result = redisTemplate.ZScanWithScore(ZSetNameKey, regex
        , pageStatus.getPageSize(), pageStatus.getPageNumber() * pageStatus.getPageSize()) ; 
        System.out.println("<BookDaoImpl SearchByName> Find Total Element Number:" +result.getMatchedElements());
        // String[] list = redisTemplate.rangeByLex(ZSetNameKey,range , pageStatus.getPageNumber() * pageStatus.getPageSize()
        // , pageStatus.getPageSize()).toArray(new String[0]) ; 
        Double[] list = result.getList().toArray(new Double[0]) ; 
        Book[] booklist = new Book[list.length] ; 
        int cnt = 0 ; 
        for (Double bookid : list) {
            // System.out.println("<BookDaoImpl SearchByName> receive Integer:" + bookid.intValue());
            String bookJSON = (String)redisTemplate.get("book" + bookid.intValue()) ; 
            booklist[cnt] = (Book)JSON.parseObject(bookJSON , Book.class) ; 
            cnt ++ ; 
        }

        return PageUtils.toPage(booklist, pageStatus, result.getMatchedElements()) ; 
//        return accessBook.SearchByName(name , pageStatus) ; 
    }

    public int modifyBook(int book_id , String name , String author , int Storage ,  String isbn , double price ) 
    {
        initializeBookZSet();
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
        initializeBookZSet();
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
        BookCover result = accessCover.save(new BookCover(book_id, cover)) ; 
        // Book toInsert = findByBookId(book_id) ; 
        // System.out.println("Book " +book_id + "not in the redis , insert in redis use find when modify");
        return 1 ; 
    }

    public int addBook(String name ,double price ,String author, String description , int storage , String isbn , String cover) 
    {
        initializeBookZSet();
        // use writethrough strategy
        Book book = accessBook.save(new Book(name, price, author, description, storage, isbn, "")) ;
        accessCover.save(new BookCover(book.getBookId(), cover)) ; 
        // insert it in the redis
        System.out.println("Book " +book.getBookId()+ "insert in redis when addbook");
        redisTemplate.set("book"+book.getBookId(), JSON.toJSONString(book));
        return 1 ; 
    }

    public int deleteBook(int book_id)
    {
        initializeBookZSet();
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
        initializeBookZSet();
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
        initializeBookZSet();
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
    
    private Book SetCover(Book book){
        BookCover cover = accessCover.findById(book.getBookId()).get() ; 
        book.setCover(cover.getBase64());
        return book ; 
    }

    private void SetCover(Page<Book> book){
        ArrayList<Integer> ids = new ArrayList<>(book.getContent().size()) ; 
        Iterator<Book> bookIter = book.getContent().iterator() ; 
        while(bookIter.hasNext()){
            ids.add(Integer.valueOf(bookIter.next().getBookId())) ; 
        }
        List<BookCover> result = accessCover.findByIdIn(ids) ;
        Iterator<BookCover> coverIter = result.iterator() ; 
        int cnt = 0 ; 
        while(coverIter.hasNext()){
            book.getContent().get(cnt).setCover(coverIter.next().getBase64());
            cnt ++ ; 
        }
    }
}