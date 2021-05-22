package com.noknow.shardingjdbcdemo;

import cn.hutool.db.Db;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.InputStream;
import java.util.Map;
import javax.sql.DataSource;
import org.yaml.snakeyaml.Yaml;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/20
 */
public class DbUtils {

  private static final Db DB;
  static {
    DB = Db.use(getDataSource());
  }

  public static Db getDb() {
    return DB;
  }

  private static DataSource getDataSource() {
    System.out.println("----------------------------test init data source begin");
    String ymlPath = "application.yml";
    InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(ymlPath);
    JSON json = JSONUtil.parse(JSONUtil.toJsonStr(new Yaml().loadAs(in, Map.class)));
    String username = json.getByPath("spring.datasource.primary.username", String.class);
    String password = json.getByPath("spring.datasource.primary.password", String.class);
    String jdbcUrl = json.getByPath("spring.datasource.primary.jdbcUrl", String.class);
    String driveClass = json.getByPath("spring.datasource.primary.driver-class-name", String.class);
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(jdbcUrl);
    config.setUsername(username);
    config.setPassword(password);
    config.setDriverClassName(driveClass);
    return new HikariDataSource(config);
  }
}
