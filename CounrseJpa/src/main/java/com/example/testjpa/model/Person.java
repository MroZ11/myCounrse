package com.example.testjpa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * Created by cloud on 2019/9/28.
 */

//2.2.0开始支持多参数返回的存储过程 方法返回为map
@NamedStoredProcedureQuery(name = "plus1", procedureName = "plus1inout2", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "arg", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "res", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "res2", type = Integer.class)})
@SQLDelete(
        sql = " UPDATE Person t SET t.delete_mark = 1 WHERE t.id = ? and t.version = ? "
)
@Where(clause = " delete_mark = 0 ")
@Entity
@Data
public class Person extends BaseModel {

    private String name;
    private Integer age;
    private String idCard;

    //FetchType.LAZY懒加载  如果有多个OneToMany同时急加载  @Fetch(FetchMode.SUBSELECT) 用 in 语句查询
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")
    @JsonIgnoreProperties({"person"})
    @Fetch(FetchMode.SUBSELECT)
    private List<Meter> meters;

    // 在JoinColumn添加foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)可以不生成外键JPA2.1新增.
    // 注意 myql外键受其引擎影响 MyISAM不支持外键；Innodb支持外键。
    // 查看默认引擎 show variables like '%storage_engine%';
    // 查看具体表引擎 show table status from db_name; (db_name对应数据库名)
    // Hibernate创建表还和 spring.jpa.database-platform 配置参数有关
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")
    private List<Pet> pets;


}
