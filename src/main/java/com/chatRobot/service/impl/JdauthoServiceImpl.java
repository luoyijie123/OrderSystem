package com.chatRobot.service.impl;


import com.chatRobot.dao.JdauthoMapper;
import com.chatRobot.model.Jdautho;
import com.chatRobot.service.JdauthoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("jdauthoService")
public class JdauthoServiceImpl implements JdauthoService {

    @Resource
    private JdauthoMapper jdauthoMapper;

    @Override
    public int deleteById(Integer id) {
        return jdauthoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int AddJdautho(Jdautho record) {
        return jdauthoMapper.insert(record);
    }

    @Override
    public Jdautho selectById(Integer id) {
        return jdauthoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Jdautho record) {
        return jdauthoMapper.updateByPrimaryKey(record);
    }
}
