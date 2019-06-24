CREATE TABLE `tbautho` (
                         `useraccount` varchar(255) NOT NULL,
                         `taobaosession` varchar(255) NOT NULL,
                         `taobaoaccount` varchar(255) DEFAULT NULL,
                         `phone` varchar(255) DEFAULT NULL,
                         `name` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`useraccount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;