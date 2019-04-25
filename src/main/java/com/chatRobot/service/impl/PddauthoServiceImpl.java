package com.chatRobot.service.impl;

import com.chatRobot.dao.PddauthoMapper;
import com.chatRobot.model.Jdautho;
import com.chatRobot.model.Pddautho;
import com.chatRobot.service.PddauthoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("pddauthoService")
public class PddauthoServiceImpl implements PddauthoService {

    @Resource
    private PddauthoMapper pddauthoMapper;

    @Override
    public int deleteById(Integer id) {
        return pddauthoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int AddPddautho(Pddautho record) {
        return pddauthoMapper.insert(record);
    }

    @Override
    public Pddautho selectById(Integer id) {
        return pddauthoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Pddautho record) {
        return pddauthoMapper.updateByPrimaryKey(record);
    }
}
