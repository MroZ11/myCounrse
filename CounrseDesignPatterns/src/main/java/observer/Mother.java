package observer;

/**
 * Created by cloud on 2019/12/23.
 */
public class Mother extends Guardian {

    public Mother(Baby baby) {
        super(baby);
    }

    @Override
    public void careBaby() {
        System.out.println(String.format(" mother care her baby[%s] ",this.baby.name));
    }
}
