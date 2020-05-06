package com.example.testjpa.model;

import lombok.Data;

import javax.persistence.Entity;

/**
 * Created by cloud on 2019/10/11.
 */
@Entity
@Data
public class Pet extends BaseModel{

    private String name;

}
