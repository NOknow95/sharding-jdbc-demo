package com.noknow.shardingjdbcdemo.prepare;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.noknow.shardingjdbcdemo.annotations.EnabledOnLocal;
import com.noknow.shardingjdbcdemo.repository.entity.User;
import com.noknow.shardingjdbcdemo.repository.mysql.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/16
 */
@SpringBootTest
public class UserDataPrepare {

  @Autowired
  private UserMapper userMapper;

  @Test
  @EnabledOnLocal
  void doPrepare() {
    for (int i = 0; i < 10; i++) {
      userMapper.insert(new User()
          .setName(StrUtil.repeat((char) ('A' + i), 3))
          .setAge(RandomUtil.randomInt(30)));
    }
  }
}
