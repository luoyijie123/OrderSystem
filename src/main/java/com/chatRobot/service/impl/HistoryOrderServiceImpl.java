package com.chatRobot.service.impl;

import com.chatRobot.dao.HistoryorderDao;
import com.chatRobot.dao.HistoryorderMapper;
import com.chatRobot.model.Historyorder;
import com.chatRobot.model.HistoryorderKey;
import com.chatRobot.service.HistoryOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("historyOrderService")
public class HistoryOrderServiceImpl implements HistoryOrderService {

    @Resource
    private HistoryorderMapper historyorderMapper;

    @Resource
    private HistoryorderDao historyorderDao;

    @Override
    public int DeleteByPrimaryKey(HistoryorderKey key) {
        return historyorderMapper.deleteByPrimaryKey(key);
    }

    @Override
    public int Add(Historyorder record) {
        return historyorderMapper.insert(record);
    }

    @Override
    public int Update(Historyorder record) {
        return historyorderMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Historyorder> SelectByUseraccount(String useraccount) {
        return historyorderDao.selectByUseraccount(useraccount);
    }
}
