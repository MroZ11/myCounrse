package cor;

/**
 * Created by cloud on 2019/12/23.
 */
public class Ceo extends Approver {

    String name = "CEO";

    @Override
    public void approve(int amount) {
        if(amount>10000){
            System.out.println(this.name+":超过最大金额[10000]，驳回");
        }else{
            System.out.println(this.name+":审批通过");
        }
    }

}
