package com.noknow.shardingjdbcdemo.repository.entity;

import cn.hutool.core.date.DateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/15
 */
@Setter
@Getter
@Accessors(chain = true)
@Entity
@Table(name = "order")
@ToString
@NoArgsConstructor
public class Order {

  public enum TagEnum {
    ELECTRONIC, CLOTHES
  }

  private Long id;
  private Long databaseKey;
  private String tag;
  private Date createdTime;

  public Order(Long id, Long databaseKey, String tag) {
    this.id = id;
    this.databaseKey = Optional.ofNullable(databaseKey).orElse(0L);
    this.tag = tag;
    this.createdTime = DateTime.now();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return Objects.equals(id, order.id)
        && Objects.equals(databaseKey, order.databaseKey)
        && Objects.equals(tag, order.tag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, databaseKey, tag);
  }
}
