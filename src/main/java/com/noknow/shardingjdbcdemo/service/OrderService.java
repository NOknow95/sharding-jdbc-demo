package com.noknow.shardingjdbcdemo.service;

import com.noknow.shardingjdbcdemo.repository.entity.Order;
import com.noknow.shardingjdbcdemo.repository.mysql.OrderRepository;
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
  private OrderRepository orderRepository;

  @Transactional(rollbackFor = Exception.class)
  public boolean saveOrder(Order order) {
//    int rows = orderRepository.insert(order);
    int rows = orderRepository.insertSelective(order);
    return rows == 1;
  }

  @Transactional(rollbackFor = Exception.class)
  public boolean deleteOrder(Long id) {
    return orderRepository.deleteById(id) == 1;
  }

  @Transactional(rollbackFor = Exception.class)
  public boolean updateOrderTag(Long id, String newTag) {
    return orderRepository.updateSelective(new Order().setId(id).setTag(newTag)) == 1;
  }

  @Transactional(rollbackFor = Exception.class)
  public Order queryOrder(Long orderId) {
    return orderRepository.query(new Order().setId(orderId));
  }

  public void testSaveUserException(Order order) {
    log.info("start to testSaveUserException");
    int rows = orderRepository.insert(order);
    log.info("testSaveUserException ok, updates:{}", rows);
    if (rows > 0) {
      log.error("testSaveUserException, updates:{}", rows);
      throw new RuntimeException("test exception");
    }
  }
}
