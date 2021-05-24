package com.noknow.shardingjdbcdemo.config.sharding;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

/**
 * <p>select the precise table through the year value of `createTime`</p>
 * <p>e.g. '2021-xx-xx xx:xx:xx' -->  audit_log_0</p>
 *
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/18
 */
@Slf4j
public class AuditLogAlgorithm implements PreciseShardingAlgorithm<Date> {

  @Override
  public String doSharding(Collection<String> availableTargetNames,
      PreciseShardingValue<Date> shardingValue) {
    String logicTableName = shardingValue.getLogicTableName();
    int year = Optional.ofNullable(shardingValue.getValue())
        .map(DateTime::of)
        .orElse(DateTime.now()).year();
    int suffix = year % 2021;
    String table = StrUtil.format("{}_{}", logicTableName, suffix);
    log.info("year:{}, suffix:{}, table:{}", year, suffix, table);
    HashSet<String> tNames = new HashSet<>(availableTargetNames);
    return tNames.contains(table) ? table : logicTableName;
  }
}
