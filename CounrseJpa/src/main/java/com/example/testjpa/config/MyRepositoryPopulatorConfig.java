package com.example.testjpa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

/**
 * 注册Jackson2RepositoryPopulatorFactoryBean根据配置初始化数据
 * 比如多个部署项目，同样的权限初始数据 可以用格式填充
 * https://www.baeldung.com/spring-data-jpa-repository-populators
 * <p>
 * 也可以用DataSourceInitializer ResourceDatabasePopulator.addScript() 执行sql
 */
@Configuration
public class MyRepositoryPopulatorConfig {

    @Value("${db_repository_populator:false}")
    private boolean allowedPopulator;

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean getRespositoryPopulator() {
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        if (allowedPopulator) {
            factory.setResources(new Resource[]{new ClassPathResource("/jsonpopulators/default.json")});
        }

        return factory;
    }


}
