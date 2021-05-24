package com.noknow.shardingjdbcdemo.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.noknow.shardingjdbcdemo.annotations.NoneTransactionalSpringTest;
import com.noknow.shardingjdbcdemo.exception.TestRunTimeException;
import com.noknow.shardingjdbcdemo.repository.entity.AuditLog;
import com.noknow.shardingjdbcdemo.repository.entity.Order;
import com.noknow.shardingjdbcdemo.repository.entity.User;
import com.noknow.shardingjdbcdemo.repository.mapper.UserMapper;
import com.noknow.shardingjdbcdemo.repository.sharding.mapper.AuditLogMapper;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/17
 */
@SuppressWarnings("all")
@NoneTransactionalSpringTest
@TestInstance(Lifecycle.PER_CLASS)
public class TransactionTest {

  private static final Class E_CLASS = TestRunTimeException.class;
  @Autowired
  private UserService userService;
  @Autowired
  private OrderService orderService;
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private AuditLogMapper auditLogMapper;

  private User user;
  private AuditLog auditLogCondition;
  private DateTime now;

  @BeforeEach
  void beforeEach() {
    String name = RandomUtil.randomString(8);
    int age = RandomUtil.randomInt(25);
    user = new User(name, age);

    now = DateTime.now().setField(DateField.MILLISECOND, 0);
    auditLogCondition = new AuditLog().setCreateTime(now);
  }

  @Test
  @DisplayName("primary transaction")
  void primaryTransactionSave() {
    user.setName(StrUtil.format("primary-{}", user.getName()));
    Console.log("user:{}", user);
    Assertions.assertThrows(E_CLASS, () -> userService.primaryTransactionSave(user, now));

    List<User> users = userMapper.select(user);
    Assertions.assertEquals(0, users.size());
    Console.log("------>save user[{}] action got rollback", user);
    List<AuditLog> auditLogs = auditLogMapper.select(auditLogCondition
        .setDescription(StrUtil.format("userId:[{}]", user.getId())));
    Assertions.assertEquals(1, auditLogs.size());
    Console.log("------>save audit log[{}] action does not get rollback", auditLogs.get(0));
  }

  @Test
  @DisplayName("sharding transaction")
  void shardingTransactionSave() {
    user.setName(StrUtil.format("sharding-{}", user.getName()));
    Console.log("user:{}", user);
    Assertions.assertThrows(E_CLASS, () -> userService.shardingTransactionSave(user, now));

    List<User> users = userMapper.select(user);
    Assertions.assertEquals(1, users.size());
    Console.log("------>save user[{}] action does not get rollback", users.get(0));
    List<AuditLog> auditLogs = auditLogMapper.select(auditLogCondition
        .setDescription(StrUtil.format("userId:[{}]", users.get(0).getId())));
    Assertions.assertEquals(0, auditLogs.size());
    Console.log("------>save audit log action does got rollback");
  }

//  @Test
//  @DisplayName("xa transaction - 2 same data sources")
//  void xaTransactionSave() {
//    user.setName(StrUtil.format("xa-{}", user.getName()));
//    Console.log("user:{}", user);
//    Assertions.assertThrows(E_CLASS, () -> userService.xaTransactionSave(user, now));
//
//    List<User> users = userMapper.select(user);
//    Assertions.assertEquals(0, users.size());
//    List<AuditLog> auditLogs = auditLogMapper.select(auditLogCondition
//        .setDescription(StrUtil.format("userId:[{}]", user.getId())));
//    Assertions.assertEquals(0, auditLogs.size());
//    Console.log("------>save user & audit log action does got rollback");
//  }
//
//  @Test
//  @DisplayName("xa transaction - 2 difference data sources")
//  void xaTransactionSave1() {
//    List<Order> orders = CollUtil.newArrayList(
//        new Order(0L, 0L, "xa-test0"),
//        new Order(1L, 1L, "xa-test1")
//    );
//    Console.log("orders:{}", orders);
//    Assertions.assertThrows(E_CLASS, () -> orderService.saveOrders(orders, true));
//  }
}
