package com.example.testac.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_ID;
import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_NAME;


/**
 * Created by cloud on 2019/03/26.
 */
@Controller
public class IndexController {
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;
    @Autowired
    HistoryService histiryservice;


    @GetMapping("/")
    public String home(ModelMap modelMap) {
        List<Model> modelList = repositoryService.createModelQuery().orderByCreateTime().desc().list();
        List<ProcessDefinition> proDeList = repositoryService.createProcessDefinitionQuery().list();
        List<ProcessInstance> proInsList = runtimeService.createProcessInstanceQuery().list();
        List<HistoricProcessInstance> hisList = histiryservice.createHistoricProcessInstanceQuery().finished().list();


        //模板
        modelMap.put("modelList",modelList);
        //流程定义
        modelMap.put("proDeList",proDeList);
        //流程实例
        modelMap.put("proInsList",proInsList);
        //流程实例
        modelMap.put("hisList",hisList);
        return "home";
    }



}
