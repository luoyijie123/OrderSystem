package com.chatRobot.service;

import com.chatRobot.model.Jdautho;
import com.chatRobot.model.Pddautho;

public interface PddauthoService {
    int deleteById(Integer id);

    int AddPddautho(Pddautho record);

    Pddautho selectById(Integer id);

    int update(Pddautho record);
}
