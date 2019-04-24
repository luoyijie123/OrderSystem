CREATE TABLE `Pddautho` (
                             `id` int not null primary key auto_increment,
                             `pdd_client_id` varchar(255) NOT NULL,
                             `pdd_client_secret` varchar(255) NOT NULL,
                             `ddk_id` varchar(255),
                             `pdd_user_account` varchar(255) NOT NULL,
                             `phone` varchar(255),
                             `name` varchar(255),
                             CONSTRAINT `pdd_user_account` FOREIGN KEY (`pdd_user_account`) REFERENCES `user` (`account`)  on delete restrict   ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;