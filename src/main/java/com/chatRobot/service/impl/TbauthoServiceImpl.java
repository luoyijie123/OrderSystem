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
    public int deleteById(Integer id) {
        return tbauthoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int AddTbautho(Tbautho record) {
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

    @Override
    public List<Tbautho> findAll() {
        return tbauthoDao.findAll();
    }

    @Override
    public List<Tbautho> selectByUserAccount(String useraccount) {
        return tbauthoDao.findByUseraccount(useraccount);
    }
}
