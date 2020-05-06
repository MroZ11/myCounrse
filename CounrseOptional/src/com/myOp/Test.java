package com.myOp;

import java.util.Optional;

/**
 * Created by cloud on 2019/02/13.
 */
public class Test {

    public static void main(String[] args) {

        System.out.println("用户穿什么鞋？");

        /*如果user为空*/
        User emptyUser = null;


        //未使用判空会出先空指针异常
        /*System.out.println(user.getShoes().getBand());*/


        //传统判空
        if (emptyUser!=null&&emptyUser.getShoes()!=null&&emptyUser.getShoes().getBand()!=null){
            System.out.println(emptyUser.getShoes().getBand());
        }else {
            System.out.println("没有鞋");
        }

        //Optional判断
        String band1 = Optional.ofNullable(emptyUser).map(User::getShoes).map(Shoes::getBand).orElse("没有鞋");
        System.out.println(band1);


        /*如果band为空*/
        User user = new User("周十五",new Shoes(null,800.0));
        //传统判空
        if(user.getShoes()!=null&&user.getShoes().getBand()!=null){
            System.out.println(user.getShoes().getBand());
        }else{
            System.out.println("没有鞋");
        }

        //Optional判断
        String band = Optional.of(user).map(User::getShoes).map(Shoes::getBand).orElse("没有鞋");
        System.out.println(band);

    }


}
