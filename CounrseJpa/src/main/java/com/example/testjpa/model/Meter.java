package com.example.testjpa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by cloud on 2019/9/28.
 */
@Entity
@Data
//如果有veison 要把version带上
@SQLDelete(
        sql = "UPDATE Meter t SET t.delete_mark = 1 WHERE t.id = ? and t.version = ? "
)
//解决Lombok生成的toString时 调用toSring死循环堆栈溢出 或者自己重写toString
@ToString(exclude = {"person"})
//@Where(clause = " delete_mark = 0 ")
public class Meter extends BaseModel {

    private String code;
    private String type;

    @ManyToOne
    @JsonIgnoreProperties({"meters", "pets"})
    private Person person;


}
