package com.example.testjpa.dto;

import lombok.Value;

import java.util.Collection;
import java.util.List;

/**
 * Created by cloud on 2019/10/8.
 * <br/>
 * <br/>
 * 官方说Dto不支持嵌套和代理
 *
 * 例如 select new PersonDto(p.id,p.meterList) from Person 是不支持的
 *
 * 只支持单行数据  一对一 和 多对一 但是也不能嵌套过滤关联对象属性
 *
 * 想要实现的话要用接口方式(接口方式又会查询全部字段 这点有点坑)
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projection.dynamic
 *
 */
@Value
public class PersonDto {

    String name;
    Integer age;
}
