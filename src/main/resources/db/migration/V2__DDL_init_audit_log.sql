drop table if exists audit_log;

create table audit_log
(
    id          varchar(32) primary key,
    description text,
    create_time datetime,
    creator     varchar(32)
)