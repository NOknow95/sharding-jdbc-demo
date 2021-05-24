package com.noknow.shardingjdbcdemo.config.sharding;

import cn.hutool.core.map.MapUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.core.yaml.swapper.ShardingRuleConfigurationYamlSwapper;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.spring.boot.common.SpringBootPropertiesConfigurationProperties;
import org.apache.shardingsphere.shardingjdbc.spring.boot.sharding.SpringBootShardingRuleConfigurationProperties;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/17
 */
@Configuration
@EnableConfigurationProperties({SpringBootShardingRuleConfigurationProperties.class,
    SpringBootPropertiesConfigurationProperties.class})
@MapperScan(basePackages = "com.noknow.shardingjdbcdemo.repository.sharding",
    annotationClass = Mapper.class,
    sqlSessionFactoryRef = "shardingSqlSessionFactory",
    sqlSessionTemplateRef = "shardingSqlSessionTemplate"
)
@Slf4j
@SuppressWarnings("all")
public class ShardingDataSourceConfig {

  /**
   * depends on flyway initialization
   */
  @Autowired
  private FlywayMigrationInitializer flywayMigrationInitializer;

  @ConfigurationProperties(prefix = "spring.datasource.test")
  @Bean("testDataSourceProps")
  public HikariConfig primaryDatasourceProps() {
    return new HikariConfig();
  }

  @Bean("testDataSource")
  public DataSource primaryDatasource(@Qualifier("testDataSourceProps") HikariConfig config) {
    return new HikariDataSource(config);
  }

  @Bean("shardingDataSource")
  //@Primary
  public DataSource shardingDatasource(@Qualifier("primaryDataSource") DataSource dataSource,
      @Qualifier("testDataSource") DataSource dataSource1,
      SpringBootPropertiesConfigurationProperties shardingProps,
      SpringBootShardingRuleConfigurationProperties shardingRule) throws SQLException {
    Map<String, DataSource> dataSourceMap = MapUtil
        .builder("test_database0", dataSource)
        .put("test_database1", dataSource1)
        .build();
    log.info("shardingProps:{}", shardingProps);
    return ShardingDataSourceFactory.createDataSource(dataSourceMap,
        new ShardingRuleConfigurationYamlSwapper().swap(shardingRule),
        shardingProps.getProps());
  }

  @Bean(name = "shardingTransactionManager")
  //@Primary
  public DataSourceTransactionManager shardingTransactionManager(
      @Qualifier("shardingDataSource") DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean(name = "shardingSqlSessionFactory")
  //@Primary
  public SqlSessionFactory shardingSqlSessionFactory(
      @Qualifier("shardingDataSource") DataSource dataSource) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    return bean.getObject();
  }

  @Bean(name = "shardingJdbcTemplate")
  //@Primary
  public JdbcTemplate shardingJdbcTemplate(@Qualifier("shardingDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean(name = "shardingSqlSessionTemplate")
  //@Primary
  public SqlSessionTemplate shardingSqlSessionTemplate(
      @Qualifier("shardingSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
    sqlSessionFactory.getConfiguration().setCallSettersOnNulls(true);
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}
