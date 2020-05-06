package com.zs;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Security自定义配置
 */
@Configuration
public class MySecurityConf extends WebSecurityConfigurerAdapter {

    @Autowired
    SecurityUtil securityUtil;

    @Resource
    MyInMemoryUserDetailsManager userDetailsService;


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


        UserDetails user = User.builder().username("admin").password("atmeter911").authorities("ROLE_ACTIVITI_ADMIN").build();
        UserDetails user2 = User.builder().username("salaboy").password("1").authorities("ROLE_ACTIVITI_USER", "GROUP_activitiTeam").build();
        UserDetails user3 = User.builder().username("ryandawsonuk").password("1").authorities("ROLE_ACTIVITI_USER", "GROUP_otherTeam").build();

        userDetailsService.createUser(user);
        userDetailsService.createUser(user2);
        userDetailsService.createUser(user3);

        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());

        //auth.inMemoryAuthentication().withUser(user).withUser(user2).withUser(user3).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }


    /*@Override  //这种方法是我手动去读用户 访问controller时 加载activiti用户
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                httpServletRequest.getSession().setAttribute("user", authentication.getName());
            }
        }).and().authorizeRequests().anyRequest().authenticated();




        http.addFilterAfter(new HttpFilter(){
            @Override
            protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
                String requestUri = request.getRequestURI();
                String contextPath = request.getContextPath();
                String url = requestUri.substring(contextPath.length());


                *//*if(url.startsWith("/actapi/")&&request.getSession().getAttribute("user")!=null){
                    securityUtil.logInAs(request.getSession().getAttribute("user").toString());
                }*//*
                chain.doFilter(request, response);
            }
        }, UsernamePasswordAuthenticationFilter.class);
    }*/



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 其实开启会话策略 会保存ACTIVITI_REMEMBER_ME 到会话session 就不用每次都用工具类 手动登录了
        // 但如果第三方api访问就可能要借助工具类手动登录了

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .and().formLogin().and().authorizeRequests().anyRequest().authenticated();
    }



}
