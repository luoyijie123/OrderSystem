package com.chatRobot.service;

import com.chatRobot.model.User;

import java.util.List;

public interface UserService {

    //新增用户
    void addUser(User user);

    //修改用户
    boolean updateUser(User user);

    //用户登录
    User login(String account,String password);

    //根据account获取user
    User getUserByaccount(String account);

    //获取所有user
    List<User> findAll();
}
