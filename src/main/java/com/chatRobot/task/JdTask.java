package com.chatRobot.task;

import com.chatRobot.service.OrderService;
import com.chatRobot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JdTask {

    //注入Service
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;
}
