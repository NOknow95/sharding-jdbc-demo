package com.noknow.shardingjdbcdemo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cn.hutool.json.JSONUtil;
import com.noknow.shardingjdbcdemo.repository.entity.Order;
import java.util.Date;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(transactionManager = "shardingTransactionManager")
@TestInstance(Lifecycle.PER_CLASS)
class OrderServiceTest {

  @Autowired
  private OrderService orderService;

  static Stream<Order> orderProvider(){
    Order o1 = new Order().setId(1L).setTag("electronic").setCreatedTime(new Date());
    Order o2 = new Order().setTag("electronic").setCreatedTime(new Date());
    return Stream.of(o1, o2);
  }

  @ParameterizedTest
  @MethodSource("orderProvider")
  void saveOrder(Order order) {
    boolean success = orderService.saveOrder(order);
    assertTrue(success, "save an order failed");
  }

  @Test
  void deleteOrder() {
    Order order = new Order().setId(1L).setTag("electronic").setCreatedTime(new Date());
    orderService.saveOrder(order);
    assertTrue(orderService.deleteOrder(1L), "delete by id failed");
  }

  @Test
  void updateOrderTag() {
    Order order = new Order().setId(1L).setTag("electronic").setCreatedTime(new Date());
    orderService.saveOrder(order);
    assertTrue(orderService.updateOrderTag(1L, "commodity"), "update order failed");
  }

  @Test
  void queryOrder() {
    Order order = new Order().setId(1L).setTag("electronic").setCreatedTime(new Date());
    orderService.saveOrder(order);
    Order orderQueried = orderService.queryOrder(1L);
    System.out.println(JSONUtil.toJsonPrettyStr(order));
    System.out.println(JSONUtil.toJsonPrettyStr(orderQueried));
    assertEquals(orderQueried, order, "query one is not equals to exists one.");
  }

  @Test
  void testSaveException() {
  }
}