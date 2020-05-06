package adapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cloud on 2019/12/19.
 * https://blog.csdn.net/wwwdc1012/article/details/82780560
 * <p>
 * 适配器模式 可以理解为对switch case的扩展 根据条件找到对应的适配器 对元数据或元对象进行处理
 */
public class AdapterRegistry {

    private List<PhoneChargingAdapter> adapters = new LinkedList<PhoneChargingAdapter>();

    public AdapterRegistry() {
        this.adapters.add(new ChinaPhoneChargingAdapter());
        this.adapters.add(new JapanPhoneChargeingAdapter());
    }

    public static void main(String[] args) {
        AdapterRegistry adapterRegistry = new AdapterRegistry();
        adapterRegistry.registryMorePhoneChargingAdapter(new PhoneChargingAdapter() {
            @Override
            public boolean support(AC ac) {
                return ac.outputAC() == 50;
            }

            @Override
            public int outputDC5V(AC ac) {
                System.out.println("使用自定义PhoneChargingAdapter变压适配器，输入AC:" + ac.outputAC() + "V" + "，输出DC:" + 5 + "V");
                return ac.outputAC();
            }
        });
        AC chinaAC = new AC220();
        AC japanAC = new AC110();
        AC AC50 = () -> 50;
        AC AC20 = () -> 20;
        adapterRegistry.getPowerAdapterAndOutputDC5V(chinaAC);
        adapterRegistry.getPowerAdapterAndOutputDC5V(japanAC);
        adapterRegistry.getPowerAdapterAndOutputDC5V(AC50);
        adapterRegistry.getPowerAdapterAndOutputDC5V(AC20);
    }

    public AdapterRegistry registryMorePhoneChargingAdapter(PhoneChargingAdapter otherPhoneChargingAdapter) {
        this.adapters.add(otherPhoneChargingAdapter);
        return this;
    }

    // 根据电压找合适的变压器
    public void getPowerAdapterAndOutputDC5V(AC ac) {
        PhoneChargingAdapter adapter = null;
        for (PhoneChargingAdapter ad : this.adapters) {
            if (ad.support(ac)) {
                adapter = ad;
                adapter.outputDC5V(ac);
                break;
            }
        }
        if (adapter == null) {
            throw new IllegalArgumentException("没有找到合适的变压适配器");
        }
    }


}
