CREATE TABLE `Jdautho` (
  `id` int not null primary key auto_increment,
  `Jd_appKey` varchar(255) NOT NULL,
  `Jd_appSecret` varchar(255) NOT NULL,
  `Jd_Access_token` varchar(255) NOT NULL,
  `jdunionid` varchar(255) NOT NULL,
  `jd_user_account` varchar(255) NOT NULL,
  `phone` varchar(255),
  `name` varchar(255),
  CONSTRAINT `user_account` FOREIGN KEY (`jd_user_account`) REFERENCES `user` (`account`)  on delete restrict   ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;