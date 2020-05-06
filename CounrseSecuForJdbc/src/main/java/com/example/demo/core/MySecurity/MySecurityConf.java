package com.example.demo.core.MySecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Security自定义配置
 */
@Configuration
public class MySecurityConf extends WebSecurityConfigurerAdapter {

    @Resource
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login/index")      //登陆页面(根据这个值会设置默认的ProcessingUrl failureUrl loginout成功地址)
                .loginProcessingUrl("/login")  // 自定义的登录接口
                .failureUrl("/login/index?loginerr=1")           // 失败地址
                .defaultSuccessUrl("/")     //成功地址 如果是ajax请求
                .and()
                .authorizeRequests()            //认证请求
                .antMatchers("/login/index", "/favicon.ico").permitAll()   //允许所有人
                .anyRequest().authenticated()   //所有请求身份验证通过的都可以访问
                .and();
                /*关闭csrf防护  .csrf().disable()*/
    }

    /**
     * 将userDetailsService配置成自己的userDetailsService
     * <P> 新版本需要指定passwordEncoder加密方式<P/>
     * {@link org.springframework.security.crypto.password.PasswordEncoder}
     * 有官方的实现也可以自定义
     *
     * @param auth {@link AuthenticationManagerBuilder}
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //.withDefaultSchema() 不要用这个 这个会去建表 手动建表就行
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

}
