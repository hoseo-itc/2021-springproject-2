CREATE TABLE USER (
id varchar(255) not null,
		name varchar(255) not null,
		phone varchar(255),
		primary key(id,name));

create table address
		(id varchar(255) not null,
		address varchar(255) not null);

create table review
		(id varchar(255) not null,
		review_date date not null,
		shopname varchar(255) not null,
		review varchar(500),
		star smallint not null);

CREATE TABLE CATEGORY (
  category_name varchar(255) not null,
  primary key(category_name)
);

CREATE TABLE RESTAURANT (
  shop_name varchar(255) not null,
  category varchar(255) not null,
  phone varchar(15),
  thumbnail_photo varchar(255),
  opening_hour char(5),
  closing_hour char(5),
  cnt_review int,
  cnt_owner_comment varchar(255),
  cnt_like int,
  primary key(shop_name),
  foreign key(category) references CATEGORY (category_name)
);

CREATE TABLE MENU (
  shop_name varchar(255) not null,
  menu_name varchar(255) not null,
  food_photo varchar(255),
  cost int,
  primary key(shop_name, menu_name),
  foreign key(shop_name) references RESTAURANT (shop_name)
)