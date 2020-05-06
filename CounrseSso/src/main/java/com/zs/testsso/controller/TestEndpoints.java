package com.zs.testsso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by cloud on 2019/5/10.
 */
@RestController
public class TestEndpoints {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/product/{id}")
    @PreAuthorize("hasAuthority('PRJ')")
    public String getProduct(@PathVariable String id,Authentication authentication) {
        return authentication.getName()+", your product id : " + id;
    }

    @GetMapping("/order/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public String getOrder(@PathVariable String id) {
        //for debug
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName()+", your order id : " + id;
    }



}
