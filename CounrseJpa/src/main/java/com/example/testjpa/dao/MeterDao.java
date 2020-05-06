package com.example.testjpa.dao;


import com.example.testjpa.model.Meter;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.stream.Stream;

/**
 * Created by cloud on 2019/9/28.
 */
public interface MeterDao extends JpaRepository<Meter, Integer>, QuerydslPredicateExecutor<Meter> {
    @EntityGraph
    Stream<Meter> getByCode(String code);
}
