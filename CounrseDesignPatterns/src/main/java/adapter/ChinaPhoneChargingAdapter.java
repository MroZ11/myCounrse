package adapter;

/**
 * Created by cloud on 2019/12/19.
 */
public class ChinaPhoneChargingAdapter implements PhoneChargingAdapter {

    private int voltage = 220;


    @Override
    public boolean support(AC ac) {
        return voltage==ac.outputAC();
    }

    @Override
    public int outputDC5V(AC ac) {
        int adapterInput = ac.outputAC();
        //变压器...
        int adapterOutput = adapterInput / 44;
        System.out.println("使用ChinaPhoneChargingAdapter变压适配器，输入AC:" + adapterInput + "V" + "，输出DC:" + adapterOutput + "V");
        return adapterOutput;
    }
}
