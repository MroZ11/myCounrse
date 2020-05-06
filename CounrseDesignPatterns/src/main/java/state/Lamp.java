package state;

/**
 * 类的行为是基于它的状态改变的。这种类型的设计模式属于行为型模式。
 *
 * 将各种具体的状态类抽象出来。
 */
public class Lamp {

    private Switcher switcher;

    public Lamp(Switcher switcher) {
        this.switcher = switcher;
    }

    public static void main(String[] args) {
        Lamp lamp = new Lamp(new Switcher());
        lamp.switcher.switcherOn();
        lamp.switcher.switcherOff();
        lamp.switcher.switcherOff();
    }

}
