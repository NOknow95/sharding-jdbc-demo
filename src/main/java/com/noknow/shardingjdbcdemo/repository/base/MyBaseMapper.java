package com.noknow.shardingjdbcdemo.repository.base;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/16
 */
public interface MyBaseMapper<T> extends Mapper<T>,
    MySqlMapper<T>, IdsMapper<T> {

}
