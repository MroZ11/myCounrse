package state.other;

/**
 * Created by cloud on 2019/12/19.
 */
public class Empty implements PlayerState {

    @Override
    public void play(Player player) {
        System.out.println(" no power play bad ");
    }
}
