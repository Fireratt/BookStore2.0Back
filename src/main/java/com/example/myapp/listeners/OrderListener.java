package com.example.myapp.listeners;
import java.util.* ; 
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.myapp.DemoApplication;
import com.example.myapp.dao.Bookdao;
import com.example.myapp.data.Book;
import com.example.myapp.data.Order;
import com.example.myapp.data.OrderItem;
import com.example.myapp.dto.Book_dto;
import com.example.myapp.service.OrderService;
import com.example.myapp.service.OrderService.StorageNotEnoughException;
import com.example.myapp.utils.StringUtils;
import com.example.myapp.websocket.WbServer;
@Component
public class OrderListener {
    @Autowired
    WbServer wbServer ; 
    @Autowired
    Bookdao accessBook ; 
    @Autowired
    OrderService orderService ; 
    @Autowired
	private KafkaTemplate<String,String> kafkaTemplate ; 

    @KafkaListener(topics={"Order"} , groupId="BookStore")
        public void topic1Listener(ConsumerRecord<String, String> order) {
        String message = order.value() ; 
        ArrayList<OrderItem> orderItems = new ArrayList<>() ; 
        List<List<String>> orderType = StringUtils.translate(message) ; 
        int size = orderType.size() ; 
        int user_id = Integer.parseInt(orderType.get(0).get(0)) ;   // user_id was placed at the header . 
        for(int i = 1 ; i < size ; i++) // read all the book in the body . 
        {
            int book_id =Integer.parseInt(orderType.get(i).get(0)) ; 
            System.out.println("AddOrder::" + book_id);
            int amount = Integer.parseInt(orderType.get(i).get(1)) ; 
            
            System.out.println(amount);
            Book bookInfo = accessBook.findByBookId(book_id) ;
            OrderItem newItem = new OrderItem(book_id, bookInfo.getName(), bookInfo.getPrice(), amount) ;
            orderItems.add(newItem) ; 
        }
        Order newOrder = new Order(-1, -1, orderItems, "") ; 
        boolean result = false; 
        try{
            result = orderService.put(newOrder, user_id) ;
        } catch(StorageNotEnoughException err)
        {
            result = false ; 
            System.out.println(err.book_id + "::" + StorageNotEnoughException.message) ;
            Book bookInfo = accessBook.findByBookId(err.book_id) ; 
            
            kafkaTemplate.send("finished",  "key", user_id + ",2;" + bookInfo.getName());//库存不足
            return ; 
        }
        if(result)
            kafkaTemplate.send("finished",  "key", user_id + ",1");//成功
        else
            {
                System.out.println("订单失败了");
                kafkaTemplate.send("finished",  "key", user_id +",0");//失败
            }
    }

    @KafkaListener(topics={"finished"} , groupId="BookStore")
    public void finishedListener(ConsumerRecord<String, String> finish)
    {
        String message = finish.value() ; 
        System.out.println("Finished Receive Message" + message);
        String[] messages = message.split(",") ; 
        String user_id = messages[0] ; 
        String toSend = messages[1] ; 
        wbServer.sendMessageToUser(user_id , toSend);
    }
}

