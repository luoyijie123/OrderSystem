package com.chatRobot.service;

import com.chatRobot.model.User;

public interface UserService {

    //新增用户
    void addUser(User user);

    //用户登录
    User login(String account,String password);
}
