CREATE TABLE `Tbautho` (
                             `id` int not null primary key auto_increment,
                             `taobao_session` varchar(255) NOT NULL,
                             `taobao_account` varchar(255),
                             `tb_user_account` varchar(255) NOT NULL,
                             `phone` varchar(255),
                             `name` varchar(255),
                             CONSTRAINT `tb_user_account` FOREIGN KEY (`tb_user_account`) REFERENCES `user` (`account`)  on delete restrict   ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;