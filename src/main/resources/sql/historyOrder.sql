CREATE TABLE `historyorder` (
                              `ordertime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              `productname` varchar(255) DEFAULT NULL,
                              `historyproductid` varchar(255) NOT NULL,
                              `historyorderid` varchar(255) NOT NULL,
                              `estimated` varchar(255) DEFAULT NULL,
                              `state` varchar(255) DEFAULT NULL,
                              `finishtime` date DEFAULT NULL,
                              `channel` varchar(255) DEFAULT NULL,
                              `weixin` varchar(255) DEFAULT NULL,
                              `refunds` varchar(255) DEFAULT NULL,
                              `issubmit` varchar(255) DEFAULT NULL,
                              `submittime` date DEFAULT NULL,
                              `useraccount` varchar(100) DEFAULT NULL,
                              `entertime` varchar(100) DEFAULT NULL,
                              PRIMARY KEY (`historyorderid`,`historyproductid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;