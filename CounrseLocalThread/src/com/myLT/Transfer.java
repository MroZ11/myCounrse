package com.myLT;


import java.util.ArrayList;
import java.util.Map;

/**
 * Created by cloud on 2019/02/12.
 */
public class Transfer implements Runnable {

    Bank bank;

    public Transfer(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            bank.set();
            bank.setB();
            System.out.println(Thread.currentThread()+""+bank.get());
            System.out.println("未使用TheadLocla的成员变量"+Thread.currentThread()+bank.getB());
        }

    }


    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();
        Transfer t = new Transfer(bank);

        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(bank.get());
        Transfer.Home.read();

    }

    static class Home{

        public static void read(){
            System.out.println("read");
        };

    }



}
