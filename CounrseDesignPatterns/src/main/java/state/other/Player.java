package state.other;

/**
 * Created by cloud on 2019/12/19.
 */
public class Player {

    private PlayerState playerState = new General();

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public void eatDoping(){
       this.playerState = new Full();
    }

    public void play(){
        this.playerState.play(this);
    }


    public static void main(String[] args) {
        Player player = new Player();
        player.play();
        player.play();
        player.eatDoping();
        player.play();
    }

}
