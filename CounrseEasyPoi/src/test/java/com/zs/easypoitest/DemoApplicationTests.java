package com.zs.easypoitest;

import com.zs.easypoitest.dao.CarDao;
import com.zs.easypoitest.service.MyService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
public class DemoApplicationTests {

	@Resource
	MyService myService;

	@Resource
	CarDao carDao;

	@Test
	public void contextLoads() throws Exception {

		Assert.assertEquals("1+2-3",URLEncoder.encode("1+2-3","UTF-8"));
		System.out.println(URLEncoder.encode("1+2-3","UTF-8"));
	}

}
