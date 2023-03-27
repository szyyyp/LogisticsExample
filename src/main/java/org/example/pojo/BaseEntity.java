package org.example.pojo;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) //用于监听实体类添加或者删除操作
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    Integer id;

    @Column(name = "sno")
    String sno;

    @Column(name = "status")
    Byte status;    // 状态

    @Column(name = "createBy")
    String createBy;
    @Column(name = "create_time", nullable = false, updatable = false)
    Date createTime;

    @Column(name = "update_time", nullable = false)
    Date updateTime;

    @PrePersist
    public void prePersist(){
        this.setCreateTime(new Date());
        this.setUpdateTime(new Date());
    }

    @PreUpdate
    public void preUpdate(){
        this.setUpdateTime(new Date());
    }
}