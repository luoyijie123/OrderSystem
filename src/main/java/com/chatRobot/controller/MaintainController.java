package com.chatRobot.controller;

import com.chatRobot.model.Order;
import com.chatRobot.service.HistoryOrderService;
import com.chatRobot.service.OrderService;
import com.chatRobot.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/maintain")
public class MaintainController {
    //注入Service
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private HistoryOrderService historyOrderService;

    @RequestMapping(value = "alterdateTime", method = RequestMethod.POST)
    public void alterdateTime(){
        List<Order> orderList = orderService.FindAll();
        for(Order order : orderList){
            if (order.getFinishTime()!=null) {
                String s = order.getFinishTime().toString();
                if(order.getFinishTime().toString().equals("1970-01-01 00:00:00.0")){
                    order.setFinishTime(null);
                    orderService.UpdateOrder(order);
                }
            }
        }
    }

    @RequestMapping(value = "alterdateTimespecail", method = RequestMethod.POST)
    public void alterdateTimespecail(){
//        List<Order> orderList = orderService.Find("83048781779");
//        Order order = orderList.get(0);

//        String s = order.getFinishTime().toString();
        }

}
