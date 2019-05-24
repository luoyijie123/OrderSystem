package com.chatRobot.service;

import com.chatRobot.model.Jdautho;
import com.chatRobot.model.Pddautho;
import com.chatRobot.model.Tbautho;

public interface TbauthoService {
    int deleteByaccount(String account);

    int AddTbautho(Tbautho record);

    Tbautho selectByaccount(String account);

    int update(Tbautho record);
}
