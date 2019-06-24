CREATE TABLE `pddautho` (
                          `useraccount` varchar(255) NOT NULL,
                          `pddclientid` varchar(255) NOT NULL,
                          `pddclientsecret` varchar(255) NOT NULL,
                          `ddkid` varchar(255) DEFAULT NULL,
                          `phone` varchar(255) DEFAULT NULL,
                          `name` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`useraccount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;