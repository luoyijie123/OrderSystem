CREATE TABLE `Pddautho` (
                             `user_account` varchar(255) not null primary key,
                             `pdd_client_id` varchar(255) NOT NULL,
                             `pdd_client_secret` varchar(255) NOT NULL,
                             `ddk_id` varchar(255),
                             `phone` varchar(255),
                             `name` varchar(255)
)ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;