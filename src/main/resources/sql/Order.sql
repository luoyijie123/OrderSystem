CREATE TABLE `productorder` (
                              `orderTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              `productName` varchar(255) DEFAULT NULL,
                              `productId` varchar(255) NOT NULL,
                              `orderId` varchar(255) NOT NULL,
                              `Estimated` varchar(255) DEFAULT NULL,
                              `state` varchar(255) DEFAULT NULL,
                              `finishTime` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              `channel` varchar(255) DEFAULT NULL,
                              `weixin` varchar(255) DEFAULT NULL,
                              `Refunds` varchar(255) DEFAULT NULL,
                              `isSubmit` varchar(255) DEFAULT NULL,
                              `submitTime` date DEFAULT NULL,
                              `useraccount` varchar(100) DEFAULT NULL,
                              `entertime` varchar(100) DEFAULT NULL,
                              `picture` varchar(100) DEFAULT NULL,
                              PRIMARY KEY (`orderId`,`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
