package com.noknow.shardingjdbcdemo.repository.sharding.mapper;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Console;
import com.noknow.shardingjdbcdemo.annotations.NoneTransactionalSpringTest;
import com.noknow.shardingjdbcdemo.repository.entity.Order;
import com.noknow.shardingjdbcdemo.repository.entity.Order.TagEnum;
import com.noknow.shardingjdbcdemo.repository.sharding.mapper.OrderMapper;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@NoneTransactionalSpringTest
class OrderMapperTest {

  @Autowired
  private OrderMapper orderMapper;

  @Test
  void insert() {
    LongStream.iterate(20, i -> i + 1).limit(20).forEach(
        i -> {
          Order order = new Order()
              .setId(i)
              .setTag(TagEnum.ELECTRONIC.name())
              .setCreatedTime(DateTime.now());
          orderMapper.insertSelective(order);
        }
    );
  }

  @Test
  void insertSelective() {
    Order order = new Order().setTag("wjw").setCreatedTime(DateTime.now());
    orderMapper.insertSelective(order);
    Console.log("order:{}", order);
  }

  @Test
  void deleteById() {
  }

  @Test
  void updateSelective() {
  }

  @Test
  void select() {
  }

  @Test
  void selectAll() {
  }
}