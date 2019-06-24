-- alter table productorder modify column orderTime Timestamp;
-- ALTER TABLE productorder ADD COLUMN useraccount VARCHAR(100);
-- ALTER TABLE productorder modify column entertime VARCHAR(100);
-- ALTER TABLE historyorder ADD column picture VARCHAR(100);
-- ALTER TABLE productorder ADD column picture VARCHAR(100);
alter table productorder modify column finishTime Timestamp;
alter table historyorder modify column finishtime Timestamp;
