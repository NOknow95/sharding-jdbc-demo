package com.noknow.shardingjdbcdemo.repository.sharding.mapper;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import com.noknow.shardingjdbcdemo.other.DbUtils;
import com.noknow.shardingjdbcdemo.annotations.NoneTransactionalSpringTest;
import com.noknow.shardingjdbcdemo.repository.entity.AuditLog;
import com.noknow.shardingjdbcdemo.repository.sharding.mapper.AuditLogMapper;
import java.sql.SQLException;
import java.util.Date;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@NoneTransactionalSpringTest
class FailedShardingTest {

  @Autowired
  private AuditLogMapper auditLogMapper;
  private static AuditLog auditLog;
  private static final boolean DELETE_LOGIC_TABLE = true;
  private static final String[] LOGIC_TABLES = {"audit_log"};

  @BeforeAll
  static void beforeAll() throws SQLException {
    dropShardingTables(LOGIC_TABLES);
    auditLog = new AuditLog().setDescription("test").setCreator("wjw").setCreateTime(new Date());
  }

  @Test
  @DisplayName("insert audit log failed when 1st time")
  void flywayInitAfterShardingInit() {
    try {
      auditLogMapper.insertSelective(auditLog);
      if (DELETE_LOGIC_TABLE) {
        org.assertj.core.api.Assertions.fail("expect insert failed but success.");
      }
    } catch (Exception e) {
      if (DELETE_LOGIC_TABLE) {
        Console.error(e, "[expected]insert failed, exception:");
      } else {
        org.assertj.core.api.Assertions.fail("expect insert failed but success.");
      }
    }
  }

  static void dropShardingTables(String... logicTables)
      throws SQLException {
    for (String table : logicTables) {
      try {
        String sql = "delete from flyway_schema_history where script like ?";
        DbUtils.getDb().execute(sql, StrUtil.format("%sharding_{}%", table));
        sql = "delete from flyway_schema_history where script like ?";
        DbUtils.getDb().execute(sql, StrUtil.format("%init_{}%", table));
        Console.log("=======> delete flyway script about {} record [OK].", table);
      } catch (SQLException e) {
        Console.log(e, "table[{}] does not exists", "flyway_schema_history");
      }
      if (DELETE_LOGIC_TABLE) {
        DbUtils.getDb().execute(StrUtil.format("drop table if exists `{}`;", table));
      }
      for (int i = 0; i < 10; i++) {
        DbUtils.getDb().execute(StrUtil.format("drop table if exists `{}_{}`;", table, i));
      }
      Console.log("=======> drop order sharding tables[{}] [OK].", table);
    }
  }
}