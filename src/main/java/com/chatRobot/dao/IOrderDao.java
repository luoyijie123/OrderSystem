package com.chatRobot.dao;

import com.chatRobot.model.Order;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;
import java.util.Map;

public interface IOrderDao {
    void saveOrder(Order order);

    boolean updateOrder(Order order);

    boolean deleteOrder(String OrderId);

    List<Order> findOrderByOrderId(String OrderId);

    List<Order> findOrderByProductId(String ProductId);

    List<Order> findAll();

    List<Order> findOrderByTimeNoproductid(Map map);

    List<Order> findOrderByTime(Map map);

    List<Order> findOrderByOrderType(String channel);

    Order findOrderByOrderIdAndUseraccount(Map map);
}
