package com.example.testac.core.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by cloud on 2019/03/27.
 */
@Component
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {//不想把在线流程图相关放在static下所以用了这样
        registry.addResourceHandler("/activiti/**").addResourceLocations("classpath:/activiti/");
    }
}
