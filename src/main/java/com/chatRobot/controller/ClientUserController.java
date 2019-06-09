package com.chatRobot.controller;

import com.chatRobot.model.Order;
import com.chatRobot.service.OrderService;
import com.chatRobot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/client")
public class ClientUserController {//客户端控制器，负责手机端业务逻辑
    //注入Service
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;



    @RequestMapping("clientHistoryOrder")
    public ModelAndView clientHistoryOrder(String useraccount,Model model){

        model.addAttribute("useraccount",useraccount);
        return new ModelAndView("clientHistoryOrder","usermodel",model);
    }

    @RequestMapping("clientSelectOrder")
    public ModelAndView clientSelectOrder(String useraccount,Model model){
        model.addAttribute("useraccount",useraccount);
        return new ModelAndView("clientSelectOrder","usermodel",model);
    }

    @RequestMapping("clientSelectOrderOperation")
    public ModelAndView clientSelectOrderOperation(String dingdanlist,Model model){

        String[] orderids = dingdanlist.split("\\r?\\n");
        List<Order> totalOrders = new ArrayList<Order>();

        for(String orderid:orderids){
            List<Order>temp = new ArrayList<Order>();
            temp = orderService.FindOrderByOrderId(orderid);

            for(int i=0;i<temp.size();i++){
                totalOrders.add(temp.get(i));
            }
        }

        model.addAttribute("orderList",totalOrders);
        return new ModelAndView("clientSelectOrder","usermodel",model);
    }

    @RequestMapping("clientUsersetting")
    public ModelAndView clientUsersetting(String useraccount,Model model){
        model.addAttribute("useraccount",useraccount);
        return new ModelAndView("clientUsersetting","usermodel",model);
    }
}
