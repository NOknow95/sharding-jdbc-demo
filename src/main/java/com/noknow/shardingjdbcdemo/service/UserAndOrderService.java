package com.noknow.shardingjdbcdemo.service;

import com.noknow.shardingjdbcdemo.repository.entity.Order;
import com.noknow.shardingjdbcdemo.repository.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/17
 */
@Service
@Slf4j
public class UserAndOrderService {

  @Autowired
  private UserService userService;

  @Autowired
  private OrderService orderService;

  @Transactional(rollbackFor = Exception.class, transactionManager = "shardingTransactionManager")
  public void saveUserAndOrder(User user, Order order) {
    log.info("saveUserAndOrder start");
    userService.register(user);
    boolean b = orderService.saveOrder(order);
    if (b) {
      throw new RuntimeException("saveUserAndOrder test error.");
    }
    log.info("saveUserAndOrder ok");
  }
}
