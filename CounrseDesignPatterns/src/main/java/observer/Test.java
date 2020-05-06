package observer;

/**
 * Created by cloud on 2019/12/23.
 * 观察者模式（Observer Pattern）
 * 一个对象（目标对象）的状态发生改变，所有的依赖对象（观察者对象）都将得到通知，进行广播通知。
 *
 * 在JAVA语言的java.util库里面，提供了一个Observable类以及一个Observer接口，构成JAVA语言对观察者模式的支持。
 * https://blog.csdn.net/hbiao68/article/details/52682858
 *
 */
public class Test {


    public static void main(String[] args) {
        Baby baby = new Baby("tom");
        baby.addGuardian(new Father(baby)).addGuardian(new Mother(baby));
        baby.cry();

    }


}
