

create table if not exists User (
	username varchar(30),
	password varchar(30),
	picture VARCHAR(2083),
	primary key (username)
) engine = InnoDB default charset = utf8;

create table if not exists Friends (
	user1 varchar(30),
	user2 varchar(30),
	primary key (user1,user2),
	constraint user1_fk foreign key (user1) references User(username) on delete cascade on update cascade,
	constraint user2_fk foreign key (user2) references User(username) on delete cascade on update cascade
) engine=InnoDB default charset=utf8;

create table if not exists Category (
	category_id mediumint not null auto_increment,
	name varchar(30) not null,
	lat float not null, 
	lon float not null,
	private boolean,
	creator varchar(30),
	primary key (category_id),
	constraint category_creator foreign key (creator) references User(Username) on delete cascade on update cascade
) engine=InnoDB default charset=utf8;

create table if not exists Happening (
	happening_id mediumint not null auto_increment, 
	starttime datetime,
	endtime datetime,
	category_id mediumint,
	private boolean,
	constraint happening_category foreign key (category_id) references Category(category_id) on delete cascade on update cascade,
	primary key (happening_id)
) engine= InnoDB default charset = utf8;

create table if not exists Follows (
	username varchar(30),
	category_id mediumint not null,
	constraint user_fk foreign key (category_id) references Category(category_id) on delete cascade on update cascade,
	primary key (username, category_id)
) engine= InnoDB default charset = utf8;
