package com.chatRobot.dao;

import com.chatRobot.model.Jdautho;
import com.chatRobot.model.Pddautho;

import java.util.List;

public interface IPddauthoDao {
    List<Pddautho> findAll();
}
