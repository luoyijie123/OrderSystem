CREATE TABLE `ProductOrder` (
  `orderTime` date,
  `productName` varchar(255),
  `productId` varchar(255) NOT NULL,
  `orderId` varchar(255) NOT NULL,
  `Estimated` varchar(255),
  `state` varchar(255),
  `finishTime` date,
  `channel` varchar(255),
  `weixin` varchar(255),
  `Refunds` varchar(255),
  `isSubmit` varchar(255),
  `submitTime` date,
  PRIMARY KEY (`orderId`,`productId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
