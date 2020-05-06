package adapter;

/**
 * Created by cloud on 2019/12/19.
 */
public class AC220 implements AC {
    public final int output = 220;

    @Override
    public int outputAC() {
        return output;
    }
}
