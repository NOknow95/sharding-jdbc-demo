drop procedure if exists pro_order_sharding;

delimiter //
create procedure pro_order_sharding(in size bigint)
begin
    declare i bigint default 0;
    while i < size
        do
            set @create_table_sql =
                    concat('create table order_', i, ' (id bigint primary key,database_key bigint not null,tag varchar(64),created_time datetime);');
            prepare create_table from @create_table_sql;
            execute create_table;
            deallocate prepare create_table;
            set i = i + 1;
        end while;
end //
delimiter ;

call pro_order_sharding(10);

drop procedure if exists pro_order_sharding;
