package com.noknow.shardingjdbcdemo.repository.mysql;

import com.noknow.shardingjdbcdemo.repository.entity.Order;
import com.noknow.shardingjdbcdemo.repository.mysql.sharding.mapper.OrderMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/15
 */
@Repository
public class OrderRepository {

  @Autowired
  private OrderMapper mapper;

  public int insert(Order order) {
    return mapper.insert(order);
  }

  public int insertSelective(Order order) {
    return mapper.insertSelective(order);
  }

  public int deleteById(Long id) {
    return mapper.deleteById(id);
  }

  public int updateSelective(Order order) {
    return mapper.updateSelective(order);
  }

  public Order query(Order order) {
    return mapper.select(order);
  }

  public List<Order> queryAll() {
    return mapper.selectAll();
  }
}
