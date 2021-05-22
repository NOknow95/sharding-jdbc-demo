drop table if exists `order`;

create table `order`
(
    id           bigint primary key,
    tag          varchar(64),
    created_time datetime
);