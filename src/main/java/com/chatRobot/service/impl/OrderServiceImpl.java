package com.chatRobot.service.impl;

import com.chatRobot.dao.IOrderDao;
import com.chatRobot.model.Order;
import com.chatRobot.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private IOrderDao orderDao;

    @Override
    public void addOrder(Order order) {
       orderDao.saveOrder(order);
    }

    @Override
    public boolean UpdateOrder(Order order) {
       return orderDao.updateOrder(order);
    }

    @Override
    public boolean DeleteOrder(String OrderId) {
        return orderDao.deleteOrder(OrderId);
    }

    @Override
    public List<Order> FindOrderByOrderId(String OrderId) {
        List<Order> orders = orderDao.findOrderByOrderId(OrderId);
        return orders;
    }

    @Override
    public List<Order> FindOrderByProductId(String ProductId) {

        List<Order> orders = orderDao.findOrderByProductId(ProductId);
        return orders;
    }

    @Override
    public List<Order> FindAll() {
        List<Order> Allorders = orderDao.findAll();
        return Allorders;
    }

    @Override
    public List<Order> FindOrderByTimeNoproductid(Map map) {
        List<Order> Allorders = orderDao.findOrderByTimeNoproductid(map);
        return  Allorders;
    }

    @Override
    public List<Order> FindOrderByTime(Map map) {
        List<Order> Allorders = orderDao.findOrderByTime(map);
        return Allorders;
    }
}
