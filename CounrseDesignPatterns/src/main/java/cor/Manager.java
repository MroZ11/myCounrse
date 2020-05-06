package cor;

/**
 * Created by cloud on 2019/12/23.
 */
public class Manager extends Approver {

    String name = "MANAGER";

    @Override
    public void approve(int amount) {
        if(amount>5000){
            System.out.println(this.name+":超过最大金额[5000]，交由上级处理");
            nextApprover.approve(amount);
        }else{
            System.out.println(this.name+":审批通过");
        }
    }

}
