package com.chatRobot.dao;

import com.chatRobot.model.Order;
import com.chatRobot.model.User;
import org.apache.ibatis.annotations.Param;

public interface IUserDao {

    //添加用户
    public void addUser(User user);

    //修改用户
    boolean updateUser(User user);

    //根据用户名和密码查询用户
    //注解的两个参数会自动封装成map集合，括号内即为键
    public User findUserByAccountAndPwd(@Param("account")String account, @Param("password")String password);

    //根据account查询用户
    public User findUserByAccount(String account);

}
