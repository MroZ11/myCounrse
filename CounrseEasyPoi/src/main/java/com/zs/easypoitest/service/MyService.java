package com.zs.easypoitest.service;

import com.zs.easypoitest.model.Car;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by cloud on 2019/4/30.
 */
public interface MyService {

    @Async
    void test();




}
