package com.noknow.shardingjdbcdemo.service;


import com.noknow.shardingjdbcdemo.repository.entity.User;
import com.noknow.shardingjdbcdemo.repository.mysql.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/16
 */
@Service
@Slf4j
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public boolean register(User user) {
    return userRepository.save(user) == 1;
  }

  public boolean unRegister(Long id) {
    return userRepository.delete(id) == 1;
  }

  public boolean update(User user) {
    return userRepository.update(user) == 1;
  }

  public User query(Long userId) {
    return userRepository.query(userId);
  }

  public void testRegisterException(User user) {
    log.info("start to testRegisterException");
    int save = userRepository.save(user);
    log.info("testRegisterException ok, updates:{}", save);
    if (save > 0) {
      log.error("testRegisterException, updates:{}", save);
      throw new RuntimeException("testRegisterException");
    }
  }
}
