package cor;

/**
 * Created by cloud on 2019/12/23.
 */
public abstract class Approver {

    protected String name;
    public Approver nextApprover;

    protected Approver setNextApprover(Approver nextApprover) {
        this.nextApprover = nextApprover;
        return this.nextApprover;// 返回下个审批人，链式编程。
    }

    public abstract void approve(int amount);// 抽象审批方法由具体审批人子类实现
}
