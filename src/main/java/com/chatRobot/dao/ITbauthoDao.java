package com.chatRobot.dao;

import com.chatRobot.model.Pddautho;
import com.chatRobot.model.Tbautho;

import java.util.List;

public interface ITbauthoDao {
    List<Tbautho> findAll();
}
