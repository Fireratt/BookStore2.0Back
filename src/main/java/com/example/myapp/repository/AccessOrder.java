package com.example.myapp.repository; 

import org.springframework.stereotype.* ;

import java.lang.reflect.Array;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate ;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.example.myapp.BookList;
import com.example.myapp.data.Book;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.annotation.Resource;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration ;
import com.example.myapp.data.Order;
import com.example.myapp.data.OrderItem;
// @Repository
// public class AccessOrder{
//     @Autowired
//     private JdbcTemplate jdbcTemplate ; 

import jakarta.transaction.Transactional;

//     public Order[] getOrderList(int user_id)
//     {
//         // use foreign key search , order it by the orderid ; so the same order will 
//         String selectSql = "select c.name , c.book_id , order_time , a.order_id , price , amount from (select * from bookdb.order where user_id = ? ) a" ; 
//         String orderItemSql = " inner join orderitem b on a.order_id = b.order_id" ; 
//         String bookSql = " inner join book c on c.book_id = b.book_id " ; 
//         String sql =selectSql + orderItemSql + bookSql + " order by order_id" ; 
//         SqlRowSet orders = jdbcTemplate.queryForRowSet(sql, new Object[]
//         {
//             user_id 
//         }) ; 
//         orders.last() ; 
//         int orderSize = orders.getRow() ; 
//         Order[] OrderList = new Order[orderSize] ; 
//         orders.beforeFirst(); 
//         int previousId = -1 ; 
//         int i = -1 ; 
//         while(orders.next())
//         {
//             int order_id = orders.getInt("order_id") ; 
//             int book_id = orders.getInt("book_id") ; 
//             int amount = orders.getInt("amount") ; 
//             double price = orders.getDouble("price") ; 
//             String order_time = orders.getString("order_time") ; 
//             String book_name = orders.getString("name") ;   // read the result
//             if(previousId==order_id)    // a old order
//             {
//                 OrderList[i].insertOrderItem(new OrderItem(book_id,book_name,price , amount)) ; 
//             }
//             else
//             {
//                 ArrayList<OrderItem> orderitems = new ArrayList<OrderItem>() ; 
//                 orderitems.add( new OrderItem(book_id,book_name,price , amount)) ; 
//                 OrderList[i+1] = new Order(order_id , user_id , orderitems , order_time) ; 
//                 previousId = order_id ;
//                 i = i + 1 ; 
//             }
//         }
//         return Arrays.copyOfRange(OrderList, 0, i+1) ; 
//     }

//     public boolean addOrder(int user_id , Order entity) 
//     {
//         try
//         {
//             String orderSql = "Insert into bookdb.order(user_id , order_time) values(?,?) " ;     // first is userid , second is time ; 
//             String getId = "SELECT LAST_INSERT_ID() as lastId" ; 
//             String itemSql = "Insert into bookdb.orderitem(order_id,book_id,amount) values(?,?,?)" ; 
//             String sql = orderSql + getId ; 

//             jdbcTemplate.update(orderSql,
//                 new Object[]
//                 { user_id , entity.getDate()}
//                 ) ; 
//                 int orderId = Integer.parseInt( jdbcTemplate.queryForMap(getId
//                 ).get("lastId").toString() ) ; 
//             int size = entity.getOrderItems().size() ; 
//             System.out.println("AddOrder:" + size);
//             ArrayList<OrderItem> orderItems = entity.getOrderItems() ; 
//             for(int i = 0 ; i < size ; i++)
//             {
//                 jdbcTemplate.update(itemSql , 
//                 new Object[]
//                 {
//                     orderId , orderItems.get(i).getBook_id() , orderItems.get(i).getAmount() 
//                 }) ; 
//             }
//             return true ; 
//         }
//         catch(Exception err)
//         {
//             System.out.println(err);
//         }
//         return false ; 
//     }
    
//     public boolean deleteOrder(int user_id , Order entity)
//     {
//         return true ; 
//     }
    
//     @Autowired
//     public AccessOrder(JdbcTemplate jdbc)
//     {
//         this.jdbcTemplate = jdbc;
//     }
// }
public interface AccessOrder extends JpaRepository<Order , Integer>{
    @Query(value="select a from Order a where a.userId = ?1" )
    public Order[] getOrderList(int user_id) ; 
    
    @Modifying
    @Transactional
    public Order save(Order order) ; 

    // @Query(value = "select o.order_id , o.book_id , o.orderitem_id , b.name , b.price , o.amount from ((select * from orderitem where order_id = ?1) o join book b on o.book_id = b.book_id)" , nativeQuery = true)
    @Query(value = "select b.book_id ,b.name , b.price , o.amount from ((select * from orderitem where order_id = ?1) o join book b on o.book_id = b.book_id)" , nativeQuery = true)
    public String[] getOrderItem(int order_id) ; 

    @Query(value="SELECT LAST_INSERT_ID() as lastId" , nativeQuery = true) 
    int getNewOrderId() ;

    @Modifying
    @Transactional
    @Query(value = "insert into orderitem(order_id , book_id , amount , price) values(?1 , ?2 , ?3 , ?4)" , nativeQuery = true)
    int saveOrderItem(int order_id , int book_id , int amount , int price) ;  

    @Query(value = "select a from Order a join OrderItem b on a.orderId = b.order_id and userId = ?1 where b.book.Name like %?2%")
    Order[] searchOrder(int user_id , String query) ; 

    @Query(value = "select a from Order a where a.userId = ?1 and a.date between ?2 and ?3")
    Order[] selectOrderByDate(int user_id , String start , String end) ; 

    @Query(value="select a from Order a")
    public Page<Order> getAllOrder(Pageable pageStatus) ; 

    @Query(value = "select a from Order a join OrderItem b on a.orderId = b.order_id where b.book.Name like %?1%")
    Page<Order> searchAllOrder(String query , Pageable pageStatus) ; 

    @Query(value = "select a from Order a where a.date between ?1 and ?2")
    Page<Order> selectAllOrderByDate(String start , String end , Pageable pageStatus) ; 
}