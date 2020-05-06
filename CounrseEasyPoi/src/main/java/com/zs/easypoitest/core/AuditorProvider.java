package com.zs.easypoitest.core;

import com.zs.easypoitest.model.Car;
import com.zs.easypoitest.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by cloud on 2019/5/17.
 */
public class AuditorProvider implements AuditorAware<String> {

    @Autowired
    EntityManager context;
    @Autowired
    MyService myService;


    @Override
    public Optional<String> getCurrentAuditor() {
        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  ra.getRequest();
        request.getSession().getAttribute("");


        return Optional.of("");
    }

}
