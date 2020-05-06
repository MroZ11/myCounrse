package state;

/**
 * Created by cloud on 2019/12/19.
 */
public class Switcher {

    private State state = new Off();

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void switcherOn(){
        state.switchOn(this);
    }

    public void switcherOff(){
        state.switchOff(this);
    }
}
