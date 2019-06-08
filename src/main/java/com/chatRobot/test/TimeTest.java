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
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = format.format(new Date());//当前入库时间
        System.out.println(new Date().toString());
    }

    public Order getOrder(){
        List<Order> orders = orderService.FindOrderByProductId("4040471820");
        Order order = orders.get(0);

        return order;
    }
}
