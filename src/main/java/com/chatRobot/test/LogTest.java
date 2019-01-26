package com.chatRobot.test;

import org.apache.log4j.Logger;

public class LogTest {
    private static Logger logger = Logger.getLogger(LogTest.class);
    public static void main(String[] args) {
        logger.debug(" This is debug!!!");
        logger.info(" This is info!!!");
        logger.warn(" This is warn!!!");
        logger.error(" This is error!!!");
        logger.fatal(" This is fatal!!!");
    }
}
