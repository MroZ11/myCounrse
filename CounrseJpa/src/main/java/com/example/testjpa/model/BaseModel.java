package com.example.testjpa.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 基础Model
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //用数字id的好处在于简单 用uuid好处在迁移融合数据方便
    @Setter
    public Integer id;

    @Version
    private Integer version;

    @Column(columnDefinition = "datetime")
    @CreatedDate
    protected Timestamp createTime;

    @Column(columnDefinition = "datetime")
    @LastModifiedDate
    protected Timestamp updateTime;

    @Setter
    @Column
    protected Integer deleteMark;

    public BaseModel() {
        this.deleteMark=0;
    }
}
