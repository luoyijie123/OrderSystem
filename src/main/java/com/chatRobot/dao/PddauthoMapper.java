package com.chatRobot.dao;

import com.chatRobot.model.Pddautho;

public interface PddauthoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pddautho
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pddautho
     *
     * @mbggenerated
     */
    int insert(Pddautho record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pddautho
     *
     * @mbggenerated
     */
    int insertSelective(Pddautho record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pddautho
     *
     * @mbggenerated
     */
    Pddautho selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pddautho
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Pddautho record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pddautho
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Pddautho record);
}