package com.chatRobot.dao;

import com.chatRobot.model.Jdautho;

public interface JdauthoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jdautho
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jdautho
     *
     * @mbggenerated
     */
    int insert(Jdautho record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jdautho
     *
     * @mbggenerated
     */
    int insertSelective(Jdautho record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jdautho
     *
     * @mbggenerated
     */
    Jdautho selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jdautho
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Jdautho record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jdautho
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Jdautho record);
}