package com.example.demo;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Created by cloud on 2020/1/9.
 */
public class Test {


    public static void main(String[] args) throws Exception {


        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("js");

        String exp = "java.lang.System.out.println(123);var a = new com.example.demo.Test();a.sayHello();  ";
        Object result = scriptEngine.eval(exp);
        System.out.println(exp + "=" + result);


    }

    public void sayHello() {
        System.out.println("Hello!!!");
    }

}
