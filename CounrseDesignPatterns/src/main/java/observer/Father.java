package observer;

/**
 * Created by cloud on 2019/12/23.
 */
public class Father extends Guardian {

    public Father(Baby baby) {
        super(baby);
    }

    @Override
    public void careBaby() {
        System.out.println(String.format(" father care his baby[%s] ",this.baby.name));
    }
}
