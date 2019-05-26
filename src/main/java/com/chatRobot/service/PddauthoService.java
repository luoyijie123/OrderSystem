package com.chatRobot.service;

import com.chatRobot.model.Pddautho;

import java.util.List;

public interface PddauthoService {
    int deleteByAccount(String id);

    int AddPddautho(Pddautho record);

    Pddautho selectByAccount(String account);

    int update(Pddautho record);

    List<Pddautho> findAll();
}
