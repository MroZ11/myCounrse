package com.example.testjpa.dto;


import com.example.testjpa.model.Meter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 实体接口只需要写属性对应的Get方法就行
 */
public interface PeronWithMeterOnly {


    String getName();

    //这里的Meter还可以修改为 Meter对应的接口
    @JsonIgnoreProperties({"person"})
    List<Meter> getMeters();
}
