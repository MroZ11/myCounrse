package com.myLT;

/**
 * Created by cloud on 2019/02/12.
 */
public class Bank {

    //使用ThreadLocal的成员变量 独立在各个线程中 不会相互影响 、JDK建议ThreadLocal定义为private static，这样ThreadLocal的弱引用问题则不存在了。
    private static ThreadLocal<Integer> t = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 100;
        }
    };

    public int get() {
        return t.get();
    }

    public void set() {
        t.set(t.get() + 10);
    }


    int b = 100;//未使用ThreadLocal的成员变量 会在各个线程中相互影响

    public int getB() {
        return b;
    }

    public void setB() {
        this.b +=10;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("销毁了");
        super.finalize();

    }
}
