package com.example.testjpa.config;

import com.alibaba.fastjson.JSON;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Created by cloud on 2019/10/30.
 */
@Component
@Aspect
public class MyControllerAop {

    @Pointcut(value = " within(com.example.testjpa.controller.*) && args(Integer) ")
    public void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        // 请求的IP
        String ip = request.getRemoteAddr();
        // 读取session中的用户
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        // 执行到当前
        System.out.println(targetName + "----->" + methodName);
        // requestParameter
        System.out.println(JSON.toJSONString(request.getParameterMap()));

        /*AopAutoConfiguration.JdkDynamicAutoProxyConfiguration*/
    }


}
