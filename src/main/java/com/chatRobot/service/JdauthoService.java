package com.chatRobot.service;

import com.chatRobot.model.Jdautho;

import java.util.List;

public interface JdauthoService {

    int deleteByUserAccount(String id);

    int AddJdautho(Jdautho record);

    Jdautho selectByUserAccount(String id);

    int update(Jdautho record);

    List<Jdautho> findAll();
}
