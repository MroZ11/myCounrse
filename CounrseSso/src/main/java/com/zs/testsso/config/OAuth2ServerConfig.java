package com.zs.testsso.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import javax.annotation.Resource;

/**
 * Created by cloud on 2019/5/10.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2ServerConfig extends WebSecurityConfigurerAdapter {

    private static final String DEMO_RESOURCE_ID = "order";



    @Resource
    MyAccessDeniedHandler myAccessDeniedHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/order/**").authenticated()
                .antMatchers("/product/**").authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(myAccessDeniedHandler)
        ;

        /*http.oauth2Client().clientRegistrationRepository(new ClientRegistrationRepository() {
            @Override
            public ClientRegistration findByRegistrationId(String registrationId) {
                return null;
            }
        });

        http.oauth2ResourceServer().configure(http);*/


    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(User.builder().username("123").password("123").authorities("USER").build())
                .withUser(User.builder().username("1234").password("1234").authorities("PRJ").build())
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }


}
