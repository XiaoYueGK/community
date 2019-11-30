create table user(
	id int PRIMARY KEY AUTO_INCREMENT,
	name varchar(50),
	account_id varchar(100),
	token VARCHAR(100),
	gmt_create LONG,
	gmt_modified LONG
);