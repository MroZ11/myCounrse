package com.zs;

import com.alibaba.fastjson.JSON;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;


@SpringBootApplication
@RestController
@RequestMapping("/actapi")
public class ActsixApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActsixApplication.class, args);
    }

    @Resource
    ProcessRuntime processRuntime;

    @Resource
    private TaskRuntime taskRuntime;

    @Resource
    private SecurityUtil securityUtil;


    @RequestMapping("")
    public String s() {
        return "222";
    }



    @RequestMapping("/g")
    @ResponseBody
    public String g() {
        String userId = org.activiti.engine.impl.identity.Authentication.getAuthenticatedUserId();
        Page<Task> taskPage =  taskRuntime.tasks(Pageable.of(0,10));
        return JSON.toJSONString(taskPage);
    }

    @RequestMapping("/i")
    @ResponseBody
    public String ss() {
        Page<Task> taskPage =  taskRuntime.tasks(Pageable.of(0,10));
        //taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(taskPage.getContent().get(0).getId()).build());
        return JSON.toJSONString(taskPage);
    }


    @RequestMapping("/v")
    @ResponseBody
    public String v() {
        Page<Task> taskPage =  taskRuntime.tasks(Pageable.of(0,10));
        //认领任务  如果任务已经被认领 会报错
        taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(taskPage.getContent().get(0).getId()).build());
        return JSON.toJSONString(taskPage);

    }


    @RequestMapping("/t")
    @ResponseBody
    public String t() {
        //访问API 需要登录角色带有 ACTIVITI_USER 角色 ，官方试例用登录工具类，也可以开启Security 会话创建策略 保存activiti remeberme
        Page<ProcessInstance> taskPage =  processRuntime.processInstances(Pageable.of(0,10));

        return JSON.toJSONString(taskPage);

    }


    @RequestMapping("/s")
    @ResponseBody
    public String start() {

        Date da = new Date();

        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("categorizeHumanProcess")
                .withName("Processing Content: " +da)
                .withVariable("content", da)
                .build());

        return  JSON.toJSONString(processInstance);
    }


}
