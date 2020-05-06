package adapter;

/**
 * Created by cloud on 2019/12/19.
 */
public interface PhoneChargingAdapter {

    boolean support(AC ac);

    int outputDC5V(AC ac);

}
