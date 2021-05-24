package com.noknow.shardingjdbcdemo.repository.mapper;

import com.noknow.shardingjdbcdemo.repository.entity.User;
import com.noknow.shardingjdbcdemo.repository.base.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/16
 */
@Mapper
public interface UserMapper extends MyBaseMapper<User> {

}
