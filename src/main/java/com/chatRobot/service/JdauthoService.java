package com.chatRobot.service;

import com.chatRobot.model.Jdautho;

import java.util.List;

public interface JdauthoService {

    int deleteById(Integer id);

    int AddJdautho(Jdautho record);

    Jdautho selectById(Integer id);

    int update(Jdautho record);

    List<Jdautho> findAll();

    List<Jdautho> selectByUserAccount(String useraccount);
}
