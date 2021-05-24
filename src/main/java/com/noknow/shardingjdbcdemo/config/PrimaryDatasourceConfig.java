package com.noknow.shardingjdbcdemo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/15
 */
@Configuration
@MapperScan(basePackages = "com.noknow.shardingjdbcdemo.repository.mapper",
    annotationClass = Mapper.class,
    sqlSessionFactoryRef = "primarySqlSessionFactory",
    sqlSessionTemplateRef = "primarySqlSessionTemplate"
)
public class PrimaryDatasourceConfig {

  @ConfigurationProperties(prefix = "spring.datasource.primary")
  @Bean("primaryDatasourceProps")
  @Primary
  public HikariConfig primaryDatasourceProps() {
    return new HikariConfig();
  }

  @Primary
  @Bean("primaryDataSource")
  public DataSource primaryDatasource(
      @Qualifier("primaryDatasourceProps") HikariConfig config) {
    return new HikariDataSource(config);
  }

  @Bean(name = "primarySqlSessionFactory")
  @Primary
  public SqlSessionFactory primarySqlSessionFactory(
      @Qualifier("primaryDataSource") DataSource dataSource) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    return bean.getObject();
  }

  @Bean(name = "primarySqlSessionTemplate")
  @Primary
  public SqlSessionTemplate primarySqlSessionTemplate(
      @Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
    sqlSessionFactory.getConfiguration().setCallSettersOnNulls(true);
    return new SqlSessionTemplate(sqlSessionFactory);
  }

  @Bean(name = "primaryjdbcTemplate")
  @Primary
  public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean(name = "primaryTransactionManager")
  @Primary
  public DataSourceTransactionManager primaryTransactionManager(
      @Qualifier("primaryDataSource") DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}
