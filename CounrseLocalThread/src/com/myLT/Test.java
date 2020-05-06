package com.myLT;

import javax.swing.text.rtf.RTFEditorKit;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by cloud on 2019/02/13.
 */
public class Test {

    ConcurrentHashMap<String,String> ct;

    public static void main(String[] args) {
        HashMap<String,String> mapTest = new HashMap<>();
        mapTest.put("1","1");
        mapTest.put("2","2");
        mapTest.put("3","3");
        mapTest.put("4","5");

        for (Map.Entry<String, String> entry:
        mapTest.entrySet()) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }

        mapTest.forEach((k,v)->{  //1.8 Lambda写法
            System.out.println(k+":"+v);
        });



        LocalDateTime time = LocalDateTime.now();
        System.out.println(time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));


        System.out.println(time.toInstant(ZoneOffset.of("+8")).toEpochMilli());

        Instant instant = Instant.ofEpochMilli(Clock.systemDefaultZone().millis());
        LocalDateTime time2 = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        System.out.println(time2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        for (int i = 0; i < 100; i++) {
            System.out.println(Math.random());
            //FIXME 没哟甩的
            System.out.println(ThreadLocalRandom.current().nextDouble(2));
        }


    }
}
