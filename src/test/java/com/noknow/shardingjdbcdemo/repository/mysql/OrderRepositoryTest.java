package com.noknow.shardingjdbcdemo.repository.mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.noknow.shardingjdbcdemo.repository.entity.Order;
import com.noknow.shardingjdbcdemo.utils.SnowflakeUtil;
import java.util.Date;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
class OrderRepositoryTest {

  @Autowired
  private OrderRepository orderRepository;
  private Order hasIdOrder;
  private Order nullIdOrder;

  @BeforeAll
  void prepare() {
    hasIdOrder = new Order().setId(1L).setTag("electronic").setCreatedTime(new Date());
    nullIdOrder = new Order().setTag("clothes").setCreatedTime(new Date());
  }

  @Test
  @DisplayName("order with id")
  void insert() {
    String msg = StrUtil.format("insert this order failed:{}", hasIdOrder);
    int updates = orderRepository.insert(hasIdOrder);
    assertEquals(1, updates, msg);
  }

  @Test
  @DisplayName("order without id")
  void insert1() {
    String msg = StrUtil.format("insert this order failed:{}", nullIdOrder);
    assertThrows(Exception.class, () -> orderRepository.insert(nullIdOrder), msg);
  }

  @ParameterizedTest
  @MethodSource("orderProvider")
  void insertSelective(Order order) {
    int updates = orderRepository.insertSelective(order);
    assertEquals(1, updates);
  }

  @Test
  void deleteById() {
  }

  @Test
  void updateSelective() {
    orderRepository.updateSelective(new Order().setTag("111"));
  }

  @Test
  @DisplayName("query by order id")
  void query() {
    Order insert = new Order().setId(SnowflakeUtil.nextId()).setTag(RandomUtil.randomString(3))
        .setCreatedTime(DateTime.now());
    orderRepository.insertSelective(insert);
    Order query = orderRepository.query(new Order().setId(insert.getId()));
    assertEquals(insert, query);
  }

  @Test
  void queryAll() {
    orderRepository.queryAll();
  }

  Stream<Order> orderProvider() {
    return Stream.of(hasIdOrder, nullIdOrder);
  }
}