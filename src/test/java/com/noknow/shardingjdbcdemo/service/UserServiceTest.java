package com.noknow.shardingjdbcdemo.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.noknow.shardingjdbcdemo.repository.entity.User;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ShardingTransactionType(TransactionType.LOCAL)
@TestInstance(Lifecycle.PER_CLASS)
class UserServiceTest {

  @Autowired
  private UserService userService;
  private User user;

  @BeforeEach
  void setUp() {
    user = new User().setName("wjw").setAge(25);
  }

  @Test
  void register() {
    boolean register = userService.register(user);
    System.out.println(JSONUtil.toJsonPrettyStr(user));
    Assertions.assertTrue(register, "save an user failed");
  }

  @Test
  void unRegister() {
    userService.register(user);
    Assertions.assertTrue(userService.unRegister(user.getId()));
  }

  @Test
  void update() {
    userService.register(user);
    User newUser = BeanUtil.copyProperties(this.user, User.class);
    newUser.setAge(RandomUtil.randomInt(256)).setName(null);
    Assertions.assertTrue(userService.update(newUser));
  }

  @Test
  void query() {
    userService.register(user);
    User query = userService.query(user.getId());
    Assertions.assertEquals(user, query);
  }
}