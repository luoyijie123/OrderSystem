package com.chatRobot.dao;

import com.chatRobot.model.Historyorder;

import java.util.List;

public interface HistoryorderDao {

    List<Historyorder> selectByUseraccount(String useraccount);
}
