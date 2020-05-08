package com.example.testjpa.form;

import org.springframework.data.web.JsonPath;
import org.springframework.data.web.ProjectedPayload;

/**
 * Created by cloud on 2020/5/8.
 */
@ProjectedPayload
public interface MeterFormPayload {

    //如果get方法和属性名对应 也可省略@JsonPath
    String getCode();

    @JsonPath("$.type")
    String getMeterType();
}
