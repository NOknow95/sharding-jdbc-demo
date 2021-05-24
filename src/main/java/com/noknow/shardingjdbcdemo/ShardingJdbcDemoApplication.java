package com.noknow.shardingjdbcdemo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class, SpringBootConfiguration.class, JtaAutoConfiguration.class
})
@ComponentScan(basePackages = "com.noknow", excludeFilters = {
    @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Mapper.class)})
public class ShardingJdbcDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShardingJdbcDemoApplication.class, args);
  }

}
