package com.noknow.shardingjdbcdemo.repository.entity;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
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
public class Order {

  private Long id;
  private String tag;
  private Date createdTime;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return Objects.equals(id, order.id) && Objects.equals(tag, order.tag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, tag);
  }
}
