package cor;

/**
 * Created by cloud on 2019/12/23.
 */
public class GroupLeader extends Approver {

    String name = "GROUP_LEADER";

    @Override
    public void approve(int amount) {
        if(amount>500){
            System.out.println(this.name+":超过最大金额[500]，交由上级处理");
            nextApprover.approve(amount);
        }else{
            System.out.println(this.name+":审批通过");
        }
    }

}
