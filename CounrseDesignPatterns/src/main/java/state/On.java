package state;

/**
 * Created by cloud on 2019/12/19.
 */
public class On implements State {

    @Override
    public void switchOn(Switcher switcher) {
        System.out.println("WARN!!!已打开无需再开");
    }

    @Override
    public void switchOff(Switcher switcher) {
        switcher.setState(new Off());
        System.out.println("OK...已关闭");
    }
}
