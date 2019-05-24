package com.chatRobot.service.impl;

import com.chatRobot.dao.PddauthoMapper;
import com.chatRobot.model.Pddautho;
import com.chatRobot.service.PddauthoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("pddauthoService")
public class PddauthoServiceImpl implements PddauthoService {

    @Resource
    private PddauthoMapper pddauthoMapper;


    @Override
    public int deleteByAccount(String id) {
        return pddauthoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int AddPddautho(Pddautho record) {
        return pddauthoMapper.insert(record);
    }

    @Override
    public Pddautho selectByAccount(String account) {
        return pddauthoMapper.selectByPrimaryKey(account);
    }

    @Override
    public int update(Pddautho record) {
        return pddauthoMapper.updateByPrimaryKey(record);
    }
}
