CREATE TABLE USER 
		(id varchar(255) not null,
		name varchar(255) not null,
		phone varchar(255),
		primary key(id,name))

create table address
		(id varchar(255) not null,
		address varchar(255) not null)

create table review
		(id varchar(255) not null,
		review_date date not null,
		shopname varchar(255) not null,
		review varchar(500),
		star smallint not null)

