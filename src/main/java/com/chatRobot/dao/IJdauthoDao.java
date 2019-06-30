package com.chatRobot.dao;

import com.chatRobot.model.Jdautho;

import java.util.List;

public interface IJdauthoDao {
    List<Jdautho> findAll();

    List<Jdautho> findByUseraccount(String useraccount);
}
