package com.noknow.shardingjdbcdemo.repository.mysql.sharding.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import com.noknow.shardingjdbcdemo.annotations.ShardingTransactionalSpringTest;
import com.noknow.shardingjdbcdemo.repository.entity.AuditLog;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ShardingTransactionalSpringTest
class AuditLogMapperTest {

  @Autowired
  private AuditLogMapper mapper;

  private static AuditLog auditLog;

  @BeforeAll
  static void beforeAll() {
    auditLog = new AuditLog().setCreateTime(DateTime.now())
        .setCreator("wjw")
        .setDescription(RandomUtil.randomString(6));
  }

  @Test
  void insert() {
    int insert = mapper.insertSelective(auditLog);
    assertEquals(1, insert);
  }

  @Test
  void insert2AllShardingTables() {
    AuditLog al = BeanUtil.copyProperties(AuditLogMapperTest.auditLog, AuditLog.class);
    al.setCreateTime(null);
    mapper.insertSelective(al);
  }

  @Test
  void selectByCreateTime() {
    Assumptions.assumeTrue(1 == mapper.insertSelective(auditLog));
    List<AuditLog> list = mapper.select(new AuditLog().setCreateTime(auditLog.getCreateTime()));
    Assertions.assertEquals(1, list.size());
    AuditLog al = list.get(0);
    Assertions.assertArrayEquals(
        new Object[]{auditLog.getDescription(), auditLog.getCreateTime(), auditLog.getCreator()},
        new Object[]{al.getDescription(), al.getCreateTime(), al.getCreator()});
  }
}