package com.chatRobot.service.impl;

import com.chatRobot.dao.TbauthoMapper;
import com.chatRobot.model.Jdautho;
import com.chatRobot.model.Tbautho;
import com.chatRobot.service.TbauthoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("tbauthoService")
public class TbauthoServiceImpl implements TbauthoService {

    @Resource
    private TbauthoMapper tbauthoMapper;

    @Override
    public int deleteById(Integer id) {
        return tbauthoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int AddPddautho(Tbautho record) {
        return tbauthoMapper.insert(record);
    }

    @Override
    public Tbautho selectById(Integer id) {
        return tbauthoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Tbautho record) {
        return tbauthoMapper.updateByPrimaryKey(record);
    }
}
