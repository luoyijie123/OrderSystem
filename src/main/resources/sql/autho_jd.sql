CREATE TABLE `Jdautho` (
  `user_account` varchar(255) not null primary key,
  `Jd_appKey` varchar(255) NOT NULL,
  `Jd_appSecret` varchar(255) NOT NULL,
  `Jd_Access_token` varchar(255) NOT NULL,
  `jdunionid` varchar(255) NOT NULL,
  `phone` varchar(255),
  `name` varchar(255)
)ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;