package com.chatRobot.service;

import com.chatRobot.model.Jdautho;

public interface JdauthoService {

    int deleteById(Integer id);

    int AddJdautho(Jdautho record);

    Jdautho selectById(Integer id);

    int update(Jdautho record);
}
