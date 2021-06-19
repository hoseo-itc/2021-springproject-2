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
  no int not null auto_increment,
  shop_name varchar(255) not null,
  shop_address varchar(255) ,
  category varchar(255) not null,
  phone varchar(15),
  thumbnail_photo varchar(255),
  opening_hour varchar(1255),
  x varchar(100),
  y varchar(100),
  cnt_review int,
  cnt_owner_comment varchar(255),
  shop_desc varchar(255),
  cnt_like int,
  primary key(no)
);

CREATE TABLE MENU (
  no int not null auto_increment,
  shop_no int not null,
  menu_name varchar(255) not null,
  food_photo varchar(255),
  cost int not null,
  primary key(no)
)

CREATE TABLE ORDER_HIS (
  his_no int not null auto_increment,
  date timestamp with time zone not null default now(),
  shopno varchar(255) not null,
  customeraddress varchar(255) not null,
  customerphone varchar(15) not null,
  allcost int not null,
  primary key(his_no)
);