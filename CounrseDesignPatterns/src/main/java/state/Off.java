package state;

/**
 * Created by cloud on 2019/12/19.
 */
public class Off implements State {

    @Override
    public void switchOn(Switcher switcher) {
        switcher.setState(new On());
        System.out.println("OK...已打开");
    }

    @Override
    public void switchOff(Switcher switcher) {
        System.out.println("WARN!!!已关闭无需再关");
    }
}
