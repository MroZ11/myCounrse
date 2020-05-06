package com.example.testac;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(exclude = {
        org.activiti.spring.boot.SecurityAutoConfiguration.class,
        SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration.class
})
public class TestacApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestacApplication.class, args);
    }

}
