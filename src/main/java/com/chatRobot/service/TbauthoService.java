package com.chatRobot.service;

import com.chatRobot.model.Jdautho;
import com.chatRobot.model.Pddautho;
import com.chatRobot.model.Tbautho;

public interface TbauthoService {
    int deleteById(Integer id);

    int AddPddautho(Tbautho record);

    Tbautho selectById(Integer id);

    int update(Tbautho record);
}
