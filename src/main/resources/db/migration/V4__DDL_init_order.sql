drop table if exists `order`;

create table `order`
(
    id           bigint primary key,
    database_key bigint not null,
    tag          varchar(64),
    created_time datetime
);