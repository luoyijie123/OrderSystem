package com.chatRobot.controller;

import com.chatRobot.service.OrderService;
import com.chatRobot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class ClientUserController {//客户端控制器，负责手机端业务逻辑
    //注入Service
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("clientHistoryOrder")
    public String clientHistoryOrder(){
        return "clientHistoryOrder";
    }

    @RequestMapping("clientSelectOrder")
    public String clientSelectOrder(){
        return "clientSelectOrder";
    }

    @RequestMapping("clientUsersetting")
    public String clientUsersetting(){
        return "clientUsersetting";
    }
}
