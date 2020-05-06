package com.zs.easypoitest.basemodel;


/**
 * Created by cloud on 2019/02/25.
 */
public class Human {

    private String name;

    private int age;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void eat(String food) {
        System.out.println(name + " eat " + food);
    }

}
