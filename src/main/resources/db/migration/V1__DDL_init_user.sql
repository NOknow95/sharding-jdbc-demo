drop table if exists `user`;

create table `user`
(
    id   bigint primary key auto_increment,
    name varchar(64),
    age  smallint
);
