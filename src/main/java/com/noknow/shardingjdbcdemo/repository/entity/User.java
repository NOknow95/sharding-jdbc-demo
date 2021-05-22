package com.noknow.shardingjdbcdemo.repository.entity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/16
 */
@Setter
@Getter
@Accessors(chain = true)
@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Integer age;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(id, user.id) && Objects.equals(name, user.name)
        && Objects.equals(age, user.age);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, age);
  }
}
