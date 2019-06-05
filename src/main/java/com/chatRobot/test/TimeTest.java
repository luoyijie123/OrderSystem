package com.chatRobot.test;

import com.chatRobot.model.Order;
import com.chatRobot.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class TimeTest {

    @Autowired
    private OrderService orderService;

    public static void main(String[] args) throws ParseException {
        TimeTest timeTest = new TimeTest();
        Order order = timeTest.getOrder();
        Timestamp timestamp = order.getOrderTime();


//        Date date = null;
//        DateFormat format = new SimpleDateFormat("yyyyMMddhh");
//        String strng_date = temp.substring(0,11);
//        date = format.parse(strng_date);
//        System.out.println(date);
    }

    public Order getOrder(){
        List<Order> orders = orderService.FindOrderByProductId("4040471820");
        Order order = orders.get(0);

        return order;
    }
}
