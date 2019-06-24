CREATE TABLE `jdautho` (
                         `useraccount` varchar(255) NOT NULL,
                         `jdappkey` varchar(255) DEFAULT NULL,
                         `jdappsecret` varchar(255) DEFAULT NULL,
                         `jdaccesstoken` varchar(255) DEFAULT NULL,
                         `jdunionid` varchar(255) DEFAULT NULL,
                         `phone` varchar(255) DEFAULT NULL,
                         `name` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`useraccount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;