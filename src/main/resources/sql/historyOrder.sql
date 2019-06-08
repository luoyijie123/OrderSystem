CREATE TABLE `historyorder` (
                              `orderTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              `productName` varchar(255) DEFAULT NULL,
                              `historyproductId` varchar(255) NOT NULL,
                              `historyorderId` varchar(255) NOT NULL,
                              `Estimated` varchar(255) DEFAULT NULL,
                              `state` varchar(255) DEFAULT NULL,
                              `finishTime` date DEFAULT NULL,
                              `channel` varchar(255) DEFAULT NULL,
                              `weixin` varchar(255) DEFAULT NULL,
                              `Refunds` varchar(255) DEFAULT NULL,
                              `isSubmit` varchar(255) DEFAULT NULL,
                              `submitTime` date DEFAULT NULL,
                              `useraccount` varchar(100) DEFAULT NULL,
                              `entertime` varchar(100) DEFAULT NULL,
                              PRIMARY KEY (`historyorderId`,`historyproductId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;