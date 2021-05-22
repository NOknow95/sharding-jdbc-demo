package com.noknow.shardingjdbcdemo.prepare;

import cn.hutool.core.util.StrUtil;
import com.noknow.shardingjdbcdemo.annotations.EnabledOnLocal;
import com.noknow.shardingjdbcdemo.repository.entity.Order;
import com.noknow.shardingjdbcdemo.repository.mysql.sharding.mapper.OrderMapper;
import com.noknow.shardingjdbcdemo.utils.SnowflakeUtil;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/16
 */
@SpringBootTest
public class OrderDataPrepare {

  @Autowired
  private OrderMapper orderMapper;

  @Test
  @EnabledOnLocal
  void doPrepare() {
    for (int i = 0; i < 100; i++) {
      Order order = new Order().setId(SnowflakeUtil.nextId())
          .setTag(StrUtil.repeat((char) ('A' + i % 26), 3))
          .setCreatedTime(new Date());
      orderMapper.insert(order);
    }
  }
}
