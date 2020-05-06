package com.example.testjpa.controller;


import com.example.testjpa.dao.MeterDao;
import com.example.testjpa.model.Meter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by cloud on 2019/9/28.
 */
@RequestMapping("/m")
@RestController
public class MeterContorller {

    @Resource
    MeterDao meterDao;

    @Resource
    EntityManager entityManager;

    @Resource
    JdbcTemplate jdbcTemplate;


    @RequestMapping("/list")
    public Object list() {


        List<Meter> meterList = meterDao.findAll();
        //entityManager.createQuery( " select  NAME as {coll.element.name} FROM Person pr "  );

        //return meterList;
        return meterList;
    }

    @RequestMapping("/d/{id}")
    public Object delete(@PathVariable("id") Integer id) {
        meterDao.deleteById(id);
        return Optional.empty();
    }


    @RequestMapping("/code/{code}")
    @Transactional
    public Object code(@PathVariable("code") String code) {

        try(Stream<Meter> meterStream = meterDao.getByCode(code)) {
            return meterStream.toArray();
        }
    }


}
