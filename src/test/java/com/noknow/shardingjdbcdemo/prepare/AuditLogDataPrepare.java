package com.noknow.shardingjdbcdemo.prepare;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import com.noknow.shardingjdbcdemo.DbUtils;
import com.noknow.shardingjdbcdemo.annotations.NoneTransactionalSpringTest;
import com.noknow.shardingjdbcdemo.annotations.EnabledOnLocal;
import com.noknow.shardingjdbcdemo.repository.entity.AuditLog;
import com.noknow.shardingjdbcdemo.repository.mysql.sharding.mapper.AuditLogMapper;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/16
 */
@NoneTransactionalSpringTest
public class AuditLogDataPrepare {

  @Autowired
  private AuditLogMapper auditLogMapper;

  @Test
  @EnabledOnLocal
  @DisplayName("prepare for logic-table")
  void prepare1() throws SQLException {
    DateTime now = DateTime.now();
    for (int i = 0; i < 1000; i++) {
      String randomStr = StrUtil.repeat((char) ('A' + i % 26), 3);
      AuditLog auditLog = new AuditLog()
          .setId(IdUtil.fastSimpleUUID())
          .setCreator(randomStr)
          .setDescription(randomStr)
          .setCreateTime(now.offsetNew(DateField.YEAR, i % 10));
      Entity entity = Entity.create("audit_log");
      entity = entity.parseBean(auditLog, true, true);
      DbUtils.getDb().insert(entity);
    }
    System.out.println("------------------------------------------");
    System.out.println("prepare success!");
  }

  @Test
  @EnabledOnLocal
  @DisplayName("prepare for sharding-table")
  void prepare2() {
    DateTime now = DateTime.now();
    for (int i = 0; i < 100; i++) {
      String randomStr = StrUtil.repeat((char) ('A' + i % 26), 3);
      AuditLog auditLog = new AuditLog()
          .setCreator(randomStr)
          .setDescription(randomStr)
          .setCreateTime(now.offsetNew(DateField.YEAR, i % 10));
      auditLogMapper.insertSelective(auditLog);
    }
    System.out.println("------------------------------------------");
    System.out.println("prepare success!");
  }
}
