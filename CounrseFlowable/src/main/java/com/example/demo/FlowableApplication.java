package com.example.demo;

import org.flowable.engine.IdentityService;
import org.flowable.engine.TaskService;
import org.flowable.form.model.SimpleFormModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class FlowableApplication {

    @Autowired
    TaskService taskService;
    @Resource
    IdentityService identityService;

    public static void main(String[] args) {
        SimpleFormModel formModel = null;


        SpringApplication.run(FlowableApplication.class, args);
    }

}
