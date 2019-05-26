package com.chatRobot.service.impl;

import com.chatRobot.dao.ITbauthoDao;
import com.chatRobot.dao.TbauthoMapper;
import com.chatRobot.model.Tbautho;
import com.chatRobot.service.TbauthoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("tbauthoService")
public class TbauthoServiceImpl implements TbauthoService {

    @Resource
    private TbauthoMapper tbauthoMapper;

    @Resource
    private ITbauthoDao tbauthoDao;


    @Override
    public int deleteByaccount(String account) {
        return tbauthoMapper.deleteByPrimaryKey(account);
    }

    @Override
    public int AddTbautho(Tbautho record) {
        return tbauthoMapper.insert(record);
    }

    @Override
    public Tbautho selectByaccount(String account) {
        return tbauthoMapper.selectByPrimaryKey(account);
    }

    @Override
    public int update(Tbautho record) {
        return tbauthoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Tbautho> findAll() {
        return tbauthoDao.findAll();
    }
}
