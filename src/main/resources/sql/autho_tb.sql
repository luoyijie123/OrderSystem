CREATE TABLE `Tbautho` (
                             `user_account` varchar(255) not null primary key,
                             `taobao_session` varchar(255) NOT NULL,
                             `taobao_account` varchar(255),
                             `phone` varchar(255),
                             `name` varchar(255)
)ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;