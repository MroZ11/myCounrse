package com.zs.easypoitest.dao;

import com.zs.easypoitest.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by cloud on 2019/03/13.
 */
public interface CarDao extends JpaRepository<Car,Integer>,JpaSpecificationExecutor<Car> {

    @Async
    CompletableFuture<Car> findCarByBrand(String brand);

}
