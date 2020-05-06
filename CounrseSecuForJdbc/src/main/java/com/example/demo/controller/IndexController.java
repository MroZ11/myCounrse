package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by cloud on 2019/4/22.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String welcome(){
        return "home";
    }

    @RequestMapping("/login/index")
    public String login(String loginerr, ModelMap map){
        if (!StringUtils.isEmpty(loginerr)){
            map.put("loginerr","账号密码错误");
        }
        return "login";
    }


}
