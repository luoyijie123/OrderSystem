package com.chatRobot.dao;

import com.chatRobot.model.Pddautho;
import com.chatRobot.model.Tbautho;

import java.util.List;

public interface IPddauthoDao {
    List<Pddautho> findAll();

    List<Pddautho> findByUseraccount(String useraccount);
}
