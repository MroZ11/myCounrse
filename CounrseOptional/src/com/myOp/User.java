package com.myOp;

/**
 * Created by cloud on 2019/02/13.
 */
public class User {

    private String name;

    private Shoes shoes;

    public User() {
    }

    public User(String name, Shoes shoes) {
        this.name = name;
        this.shoes = shoes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shoes getShoes() {
        return shoes;
    }

    public void setShoes(Shoes shoes) {
        this.shoes = shoes;
    }
}
