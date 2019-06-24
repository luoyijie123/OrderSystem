package com.chatRobot.service;

import com.chatRobot.model.Order;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;
import java.util.Map;

public interface OrderService {
    void addOrder(Order order);

    boolean UpdateOrder(Order order);

    boolean DeleteOrder(String OrderId);

    List<Order> FindOrderByOrderId(String OrderId);

    List<Order> FindOrderByProductId(String ProductId);

    List<Order> FindAll();

    List<Order> FindOrderByTimeNoproductid(Map map);

    List<Order> FindOrderByTime(Map map);

    List<Order> FindOrderByOrderType(String channel);

    Order FindOrderByOrderIdAndUseraccount(Map map);

    Order FindOrderByLastTime();
}
