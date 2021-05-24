drop procedure if exists pro_audit_log_sharding;

delimiter //
create procedure pro_audit_log_sharding(in size bigint)
begin
    declare i bigint default 0;
    while i < size
        do
            set @create_table_sql = concat('create table audit_log_', i, ' (id varchar(32) primary key,description text,create_time datetime,creator varchar(32));');
            prepare create_table from @create_table_sql;
            execute create_table;
            deallocate prepare create_table;
            set i = i + 1;
        end while;
end //
delimiter ;

call pro_audit_log_sharding(10);

drop procedure if exists pro_audit_log_sharding;
