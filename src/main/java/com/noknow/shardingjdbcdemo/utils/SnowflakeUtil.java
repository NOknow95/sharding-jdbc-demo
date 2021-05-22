package com.noknow.shardingjdbcdemo.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/16
 */
public interface SnowflakeUtil {

  Snowflake SNOWFLAKE = IdUtil.createSnowflake(1, 1);

  static long nextId() {
    return SNOWFLAKE.nextId();
  }
}
