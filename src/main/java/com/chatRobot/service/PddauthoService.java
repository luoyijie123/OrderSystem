package com.chatRobot.service;

import com.chatRobot.model.Jdautho;
import com.chatRobot.model.Pddautho;

public interface PddauthoService {
    int deleteByAccount(String id);

    int AddPddautho(Pddautho record);

    Pddautho selectByAccount(String account);

    int update(Pddautho record);
}
