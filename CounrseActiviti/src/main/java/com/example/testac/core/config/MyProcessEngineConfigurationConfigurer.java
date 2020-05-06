package com.example.testac.core.config;


import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by cloud on 2019/03/26.
 */
@Component
public class MyProcessEngineConfigurationConfigurer implements ProcessEngineConfigurationConfigurer {
    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        springProcessEngineConfiguration.setActivityFontName("宋体");
        springProcessEngineConfiguration.setLabelFontName("宋体");
        springProcessEngineConfiguration.setAnnotationFontName("宋体");
    }
}
