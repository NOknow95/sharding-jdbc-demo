package com.noknow.shardingjdbcdemo.repository.sharding.mapper;

import com.noknow.shardingjdbcdemo.repository.entity.Order;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/15
 */
@Mapper
public interface OrderMapper {

  @Insert("insert into `order` (id, tag, created_time) values (#{id}, #{tag}, #{createdTime} )")
  int insert(Order order);

  int insertSelective(Order order);

  @Delete("delete from `order` where id = #{id}")
  int deleteById(@Param("id") Long id);

  int updateSelective(Order order);

  Order select(Order order);

  @Select("select * from `order`")
  List<Order> selectAll();
}
