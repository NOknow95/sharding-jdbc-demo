package com.noknow.shardingjdbcdemo.config.sharding;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import java.util.Optional;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/18
 */
@Slf4j
public final class MySnowFlakeKeyGenerator implements ShardingKeyGenerator {

  private final Properties properties = new Properties();
  private Snowflake snowflake;

  @Override
  public Comparable<?> generateKey() {
    return snowflake.nextId();
  }

  @Override
  public String getType() {
    return "MySnowFlake";
  }

  @Override
  public Properties getProperties() {
    return this.properties;
  }

  @Override
  public void setProperties(Properties properties) {
    Optional.ofNullable(properties).ifPresent(props -> this.properties.putAll(properties));
    log.info("in-props:{}, this-props:{}", properties, this.properties);
    String workid = this.properties.getProperty("workid", "1");
    String datacenterid = this.properties.getProperty("datacenterid", "1");
    this.snowflake = IdUtil.createSnowflake(Long.parseLong(workid), Long.parseLong(datacenterid));
  }
}
