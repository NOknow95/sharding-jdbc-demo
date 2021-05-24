package com.noknow.shardingjdbcdemo.repository.entity;

import cn.hutool.core.date.DateUtil;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/19
 */
@Table(name = "audit_log")
@Entity
@Setter
@Getter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
public class AuditLog {

  @Id
  private String id;
  private String description;
  private Date createTime;
  private String creator;

  public AuditLog(String description, Date createTime, String creator) {
    this.description = description;
    setCreateTime(createTime);
    this.creator = creator;
  }

  public AuditLog setCreateTime(Date createTime) {
    this.createTime = Optional.ofNullable(createTime).map(DateUtil::beginOfSecond).orElse(null);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuditLog auditLog = (AuditLog) o;
    return Objects.equals(id, auditLog.id) && Objects
        .equals(description, auditLog.description) && Objects
        .equals(createTime, auditLog.createTime) && Objects
        .equals(creator, auditLog.creator);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, createTime, creator);
  }
}
