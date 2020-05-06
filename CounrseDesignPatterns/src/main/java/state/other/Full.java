package state.other;

/**
 * Created by cloud on 2019/12/19.
 */
public class Full implements PlayerState {

    @Override
    public void play(Player player) {
        player.setPlayerState(new General());
        System.out.println(" full power play great ");
    }
}