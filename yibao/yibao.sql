
 CREATE DATABASE yibao DEFAULT CHARACTER SET UTF8 COLLATE utf8_general_ci;

 USE yibao;

 CREATE TABLE  `user` 
 (
 	`uid` MEDIUMINT UNSIGNED AUTO_INCREMENT ,
 	`sno` CHAR(20) NOT NULL  COMMENT '学号',
 	`user_name` CHAR(10) NOT NULL COMMENT '姓名',
   `nickname` CHAR(20) NOT NULL COMMENT '昵称',
   `phone` CHAR(11) NOT NULL COMMENT '电话' ,
   `major` CHAR(20) NOT NULL COMMENT '专业',
   `grade` CHAR(5) NOT NULL COMMENT '年级', 
   `avatar_path`  VARCHAR(255) NOT NULL COMMENT '头像',
 	PRIMARY KEY(`uid`),
 	UNIQUE(`sno`)
 )ENGINE=InnoDB DEFAULT CHAR SET = 'UTF8';

 ALTER TABLE testalter_tbl ADD i INT AFTER c;
 TRUNCATE TABLE table1






