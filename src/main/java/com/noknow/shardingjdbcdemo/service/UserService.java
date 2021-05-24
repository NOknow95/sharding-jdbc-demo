package com.noknow.shardingjdbcdemo.service;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.noknow.shardingjdbcdemo.exception.TestRunTimeException;
import com.noknow.shardingjdbcdemo.repository.entity.AuditLog;
import com.noknow.shardingjdbcdemo.repository.entity.User;
import com.noknow.shardingjdbcdemo.repository.mapper.UserMapper;
import com.noknow.shardingjdbcdemo.repository.sharding.mapper.AuditLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/16
 */
@SuppressWarnings("DuplicatedCode")
@Service
@Slf4j
public class UserService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private AuditLogMapper auditLogMapper;

  public boolean register(User user) {
    return userMapper.insertSelective(user) == 1;
  }

  public boolean unRegister(Long id) {
    return userMapper.deleteByPrimaryKey(id)== 1;
  }

  public boolean update(User user) {
    return userMapper.updateByPrimaryKeySelective(user) == 1;
  }

  public User query(Long userId) {
    return userMapper.selectByPrimaryKey(userId);
  }

  @Transactional(rollbackFor = Exception.class, transactionManager = "primaryTransactionManager")
  public void primaryTransactionSave(User user, DateTime now) {
    transactionSave(user, now);
  }

  @Transactional(rollbackFor = Exception.class, transactionManager = "shardingTransactionManager")
  public void shardingTransactionSave(User user, DateTime now) {
    transactionSave(user, now);
  }

  @Transactional(rollbackFor = Exception.class, transactionManager = "xaTransactionManager")
  public void xaTransactionSave(User user, DateTime now) {
    transactionSave(user, now);
  }

  private void transactionSave(User user, DateTime now) {
    log.info("==============saveUserAndAuditLogWithException start, user:{}", user);
    int updates = userMapper.insertSelective(user);
    AuditLog al = new AuditLog(StrUtil.format("userId:[{}]", user.getId()), now, "wjw");
    if (updates == 1 && auditLogMapper.insertSelective(al) == 1) {
      log.info("----saveUserAndAuditLogWithException error, user:{}", user);
      throw new TestRunTimeException("saveUserAndOrder test error.");
    }
    log.info("==============saveUserAndAuditLogWithException success, user:{}", user);
  }
}
