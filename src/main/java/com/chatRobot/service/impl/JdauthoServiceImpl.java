package com.chatRobot.service.impl;


import com.chatRobot.dao.IJdauthoDao;
import com.chatRobot.dao.JdauthoMapper;
import com.chatRobot.model.Jdautho;
import com.chatRobot.service.JdauthoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("jdauthoService")
public class JdauthoServiceImpl implements JdauthoService {

    @Resource
    private JdauthoMapper jdauthoMapper;

    @Resource
    private IJdauthoDao jdauthoDao;


    @Override
    public int deleteByUserAccount(String id) {
        return jdauthoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int AddJdautho(Jdautho record) {
        return jdauthoMapper.insert(record);
    }

    @Override
    public Jdautho selectByUserAccount(String id) {
        return jdauthoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Jdautho record) {
        return jdauthoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Jdautho> findAll() {
        return jdauthoDao.findAll();
    }
}
