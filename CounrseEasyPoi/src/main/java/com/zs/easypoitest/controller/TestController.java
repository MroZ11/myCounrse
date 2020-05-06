package com.zs.easypoitest.controller;

import cn.afterturn.easypoi.word.WordExportUtil;
import com.zs.easypoitest.ExcleBeans.Person;
import com.zs.easypoitest.core.task.SampleJob;
import com.zs.easypoitest.core.utils.ExcelUtil;
import com.zs.easypoitest.service.MyService;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by cloud on 2019/02/19.
 */
@Controller
public class TestController {

    @Resource
    Scheduler scheduler;

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Resource
    MyService myService;


    @RequestMapping("t3")
    @ResponseBody
    public String t3() throws SchedulerException {
        System.out.println(1);
        //myService.test();
        System.out.println(3);
        return "ok";
    }


    @RequestMapping("up")
    public String login() throws SchedulerException {
        System.out.println("打印控制台");
        logger.info("有访问系统");
        return "up";
    }


    @RequestMapping(value = "upload/submit", method = RequestMethod.POST)
    public void up(MultipartFile file, HttpServletResponse response) {
        //解析excel，
        List<Person> personList = ExcelUtil.importExcel(file, 1, 1, Person.class);
        personList.forEach((person) -> {
            System.out.println(person.toString());
        });
    }

    @RequestMapping(value = "upload/export", method = RequestMethod.GET)
    public void export(HttpServletResponse response) {
        //解析excel，
        List<Person> personList = new ArrayList<>();
        Person personOne = new Person("王1", "1", new Date());
        Person personTwo = new Person("王2", "2", new Date());
        Person personThree = new Person("王3", "1", new Date());
        personList.add(personOne);
        personList.add(personTwo);
        personList.add(personThree);

        ExcelUtil.exportExcel(personList, "用户表", "1", Person.class, "用户表.xls", true, response);

    }


    @RequestMapping(value = "upload/exportWord", method = RequestMethod.GET)
    public void exportWord(HttpServletResponse response) {

        String staticPath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static\\";

        //String templatePath = "E:\\IdeaProjects\\javaCourse\\CounrseEasyPoi\\src\\main\\resources\\static\\wordTemplate\\1.docx";
        String templatePath = staticPath + "wordTemplate\\1.docx";

        String tempath = "D:\\";
        String fileName = "测试word.docx";
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("name", "周蜀");
        objectMap.put("date", "2018-12-12");
        objectMap.put("content", "你好呀");

        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 4; i++) {
            Map<String, String> lm = new HashMap<String, String>();
            lm.put("id", i + 1 + "");
            lm.put("zijin", i * 10000 + "");
            lm.put("bianma", "A001");
            lm.put("mingcheng", "设计");
            lm.put("xiangmumingcheng", "EasyPoi " + i + "期");
            lm.put("quancheng", "开源项目");
            lm.put("sqje", i * 10000 + "");
            lm.put("hdje", i * 10000 + "");

            listMap.add(lm);
        }


        objectMap.put("maplist", listMap);


        ExcelUtil.exportWord(templatePath, tempath, fileName, objectMap, response);

    }




}
