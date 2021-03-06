package com.chatRobot.service.impl;

import com.chatRobot.dao.IUserDao;
import com.chatRobot.model.User;
import com.chatRobot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Resource
    private IUserDao userDao;

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public User login(String account, String password) {
         User user=userDao.findUserByAccountAndPwd(account,password);
         if(user != null && user.getPassword().equals(password))
         {
             return user;
         }
         return null;
    }

    @Override
    public User getUserByaccount(String account) {
        return userDao.findUserByAccount(account);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
