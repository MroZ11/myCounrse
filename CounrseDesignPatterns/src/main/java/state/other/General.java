package state.other;

/**
 * Created by cloud on 2019/12/19.
 */
public class General implements PlayerState {

    @Override
    public void play(Player player) {
        System.out.println(" general power play so so ");
        player.setPlayerState(new Empty());
    }
}
