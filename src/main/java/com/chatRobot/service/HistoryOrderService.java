package com.chatRobot.service;

import com.chatRobot.model.Historyorder;
import com.chatRobot.model.HistoryorderKey;

import java.util.List;

public interface HistoryOrderService {

    int DeleteByPrimaryKey(HistoryorderKey key);

    int Add(Historyorder record);

    int Update(Historyorder record);

    List<Historyorder> SelectByUseraccount(String useraccount);
}
