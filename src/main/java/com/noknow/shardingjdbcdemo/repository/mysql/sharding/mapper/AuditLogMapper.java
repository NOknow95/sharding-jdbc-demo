package com.noknow.shardingjdbcdemo.repository.mysql.sharding.mapper;

import com.noknow.shardingjdbcdemo.repository.entity.AuditLog;
import com.noknow.shardingjdbcdemo.repository.mysql.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/19
 */
@Mapper
public interface AuditLogMapper extends MyBaseMapper<AuditLog> {
}
