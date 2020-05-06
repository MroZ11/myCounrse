package observer;

/**
 * Created by cloud on 2019/12/23.
 */
public abstract class Guardian {

    public Baby baby;

    public Guardian(Baby baby) {
        this.baby = baby;
    }

    public abstract void careBaby();
}
