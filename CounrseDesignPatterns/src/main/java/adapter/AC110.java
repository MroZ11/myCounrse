package adapter;

/**
 * Created by cloud on 2019/12/19.
 */
public class AC110 implements AC {
    public final int output = 110;

    @Override
    public int outputAC() {
        return output;
    }
}
