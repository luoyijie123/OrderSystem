package com.chatRobot.service;

import com.chatRobot.model.Jdautho;
import com.chatRobot.model.Pddautho;
import com.chatRobot.model.Tbautho;

import java.util.List;

public interface TbauthoService {
    int deleteById(Integer id);

    int AddTbautho(Tbautho record);

    Tbautho selectById(Integer id);

    int update(Tbautho record);

    List<Tbautho> findAll();

    List<Tbautho> selectByUserAccount(String useraccount);
}
