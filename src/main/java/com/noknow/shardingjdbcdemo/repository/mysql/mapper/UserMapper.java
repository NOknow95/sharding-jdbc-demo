package com.noknow.shardingjdbcdemo.repository.mysql.mapper;

import com.noknow.shardingjdbcdemo.repository.entity.User;
import com.noknow.shardingjdbcdemo.repository.mysql.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/16
 */
@Mapper
public interface UserMapper extends MyBaseMapper<User> {

}
