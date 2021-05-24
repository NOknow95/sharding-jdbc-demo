package com.noknow.shardingjdbcdemo.service;

import com.noknow.shardingjdbcdemo.exception.TestRunTimeException;
import com.noknow.shardingjdbcdemo.repository.entity.Order;
import com.noknow.shardingjdbcdemo.repository.sharding.mapper.OrderMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/15
 */
@Service
@Slf4j
public class OrderService {

  @Autowired
  private OrderMapper orderMapper;

  @Transactional(rollbackFor = Exception.class, transactionManager = "shardingTransactionManager")
  public boolean saveOrder(Order order) {
    int rows = orderMapper.insertSelective(order);
    return rows == 1;
  }

  @Transactional(rollbackFor = Exception.class, transactionManager = "shardingTransactionManager")
  public boolean deleteOrder(Long id) {
    return orderMapper.deleteById(id) == 1;
  }

  @Transactional(rollbackFor = Exception.class, transactionManager = "shardingTransactionManager")
  public boolean updateOrderTag(Long id, String newTag) {
    return orderMapper.updateSelective(new Order().setId(id).setTag(newTag)) == 1;
  }

  public Order queryOrder(Long orderId) {
    return orderMapper.select(new Order().setId(orderId));
  }

  @Transactional(rollbackFor = Exception.class)
  public void saveOrders(List<Order> orders, boolean testException) {
    Optional.ofNullable(orders).orElse(Collections.emptyList())
        .forEach(order -> orderMapper.insertSelective(order));
    if (testException) {
      throw new TestRunTimeException("save orders failed.");
    }
  }
}
