package com.chatRobot.service.impl;

import com.chatRobot.dao.IPddauthoDao;
import com.chatRobot.dao.PddauthoMapper;
import com.chatRobot.model.Pddautho;
import com.chatRobot.service.PddauthoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pddauthoService")
public class PddauthoServiceImpl implements PddauthoService {

    @Resource
    private PddauthoMapper pddauthoMapper;

    @Resource
    private IPddauthoDao pddauthoDao;


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

    @Override
    public List<Pddautho> findAll() {
        return pddauthoDao.findAll();
    }

    @Override
    public List<Pddautho> selectByUserAccount(String useraccount) {
        return pddauthoDao.findByUseraccount(useraccount);
    }
}
