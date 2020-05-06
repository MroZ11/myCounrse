package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;

/**
 * Created by cloud on 2019/6/5.
 */
@Controller
public class UserController {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserDetailsPasswordService userDetailsPasswordService;


}
