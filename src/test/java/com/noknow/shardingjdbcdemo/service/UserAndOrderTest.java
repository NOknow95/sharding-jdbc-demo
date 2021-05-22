package com.noknow.shardingjdbcdemo.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.noknow.shardingjdbcdemo.repository.entity.Order;
import com.noknow.shardingjdbcdemo.repository.entity.User;
import com.noknow.shardingjdbcdemo.utils.SnowflakeUtil;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/17
 */
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserAndOrderTest {

  @Autowired
  private UserAndOrderService service ;

  @Test
  void testSaveUserAndOrder() {
    User user = new User()
        .setAge(RandomUtil.randomInt(30))
        .setName(StrUtil.repeat((char) ('A' + RandomUtil.randomInt(26)), 3));
    Order order = new Order()
        .setId(SnowflakeUtil.nextId())
        .setTag(RandomUtil.randomString(3))
        .setCreatedTime(new Date());
    service.saveUserAndOrder(user, order);
  }
}
