package com.noknow.shardingjdbcdemo.repository.mysql;

import com.noknow.shardingjdbcdemo.repository.entity.User;
import com.noknow.shardingjdbcdemo.repository.mysql.mapper.UserMapper;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/16
 */
@Repository
public class UserRepository {

  @Autowired
  private UserMapper userMapper;

  public int save(User user) {
    return userMapper.insertSelective(user);
  }

  public int delete(Long id) {
    return Optional.ofNullable(id).map(id1 -> userMapper.deleteByPrimaryKey(id1)).orElse(0);
  }

  public int update(User user) {
    return userMapper.updateByPrimaryKeySelective(user);
  }

  public User query(Long userId) {
    return userMapper.selectByPrimaryKey(userId);
  }
}
