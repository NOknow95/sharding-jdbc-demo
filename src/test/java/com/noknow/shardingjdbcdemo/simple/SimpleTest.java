package com.noknow.shardingjdbcdemo.simple;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/15
 */
public class SimpleTest {

  @Test
  void test() {
    System.out.println(System.getProperty("os.name"));
  }

  @SuppressWarnings("unchecked")
  @Test
  void readYml() {
    String path = "application.yml";
    Yaml yaml = new Yaml();
    InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    Map map = yaml.loadAs(in, Map.class);
    String jsonStr = JSONUtil.toJsonPrettyStr(map);
    System.out.println(jsonStr);
    JSON json = JSONUtil.parse(jsonStr);
    String username = json.getByPath("spring.datasource.primary.username", String.class);
    System.out.println(username);
  }

  @Test
  void hutoolDb() throws SQLException {
    List<Entity> list = Db.use(getDataSource()).query("select count(1) from flyway_schema_history");
    Assertions.assertEquals(1, list.size());
  }

  private static DataSource getDataSource() {
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
