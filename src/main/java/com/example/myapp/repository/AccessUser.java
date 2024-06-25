package com.example.myapp.repository; 

import org.springframework.stereotype.* ; 
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ; 
import com.example.myapp.BookList;
import com.example.myapp.data.Book;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.annotation.Resource;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration ;
import com.example.myapp.data.User; 
import org.springframework.data.jpa.repository.Modifying;
import jakarta.transaction.Transactional;

// @Repository
// public class AccessUser{
//     @Autowired
//     private JdbcTemplate jdbcTemplate ; 
    
//     public User getUser(String userId)
//     {
//         String sql = "select * from user which userId = " + userId ; 
//         Map<String,Object> response =  jdbcTemplate.queryForMap(sql) ; 
//         return new User(response) ; 
//     }
//     public void setUserData(User newUserInfo)
//     {
//         String sql = "replace into user values(?,?,?,?,?) " ;
//         Map<String ,String> data = newUserInfo.getMap() ; 
//         jdbcTemplate.update(sql,
//         new Object[]{
//             data.get("id") , 
//             data.get("username") , 
//             data.get("date") , 
//             data.get("phone"),
//             data.get("mail") ,
//         }) ; 
//     }

//     @Autowired
//     public AccessUser(JdbcTemplate jdbc)
//     {
//         this.jdbcTemplate = jdbc;
//     }
// }
public interface AccessUser extends JpaRepository<User , Integer>{

    // User findById(int id) ; 

    @Query(value = "select u.administrator from User u where u.id = ?1")
    boolean checkAdministrator(int id) ; 

    @Query(value = "select u.ban from User u where u.id = ?1")
    boolean checkBan(int id) ; 
    
    @Query(value="select u from User u")
    public List<User> getUserInfo() ; 

    @Modifying
    @Transactional
    @Query(value="update User u set u.ban=true where u.id = ?1")
    public int banUser(int user_id) ; 

    @Modifying
    @Transactional
    @Query(value="update User u set u.ban=false where u.id = ?1")
    public int unbanUser(int user_id) ; 

    @Transactional
    @Query(value="call bookdb.get_user_rank(?1, ?2)" , nativeQuery = true)
    public List<Map> getUserRank(String start , String end) ; 
}