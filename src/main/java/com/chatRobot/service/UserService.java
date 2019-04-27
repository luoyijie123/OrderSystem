package com.chatRobot.service;

import com.chatRobot.model.User;

public interface UserService {

    //新增用户
    void addUser(User user);

    //修改用户
    boolean updateUser(User user);

    //用户登录
    User login(String account,String password);

    //根据account获取user
    User getUserByaccount(String account);
}
