package com.zs.easypoitest.service;

import com.zs.easypoitest.dao.CarDao;
import com.zs.easypoitest.model.Car;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * Created by cloud on 2019/4/30.
 */
@Service
public class MyServiceImpl implements MyService {
    @Resource
    CarDao carDao;

    @Override
    public void test()  {
        for (int i = 10; i < 100; i+=10) {
            System.out.println(i);
        }

    }

}
