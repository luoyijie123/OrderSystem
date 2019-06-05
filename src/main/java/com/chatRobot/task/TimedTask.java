package com.chatRobot.task;

import com.chatRobot.model.Order;
import com.chatRobot.service.OrderService;
import com.chatRobot.service.UserService;
import com.chatRobot.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TimedTask {

    //注入Service
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0/5 * * * * ? ")// 间隔5秒执行
    public void taskCycle() {
        System.out.println("使用SpringMVC框架配置定时任务");
        List<Order> orderList = orderService.FindOrderByOrderType("拼多多");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp ="";
        Order order = orderList.get(0);
        Timestamp timestamp = order.getOrderTime();
        temp = TimeUtil.StringToTimestamp(timestamp.toString());
        System.out.println(temp);
    }
}
