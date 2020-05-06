package cor;

/**
 * 责任链模式（Chain of Responsibility Pattern）
 * 典型特征是接收者都包含对另一个接收者的引用。如果一个对象不能处理该请求，那么它会把相同的请求传给下一个接收者，依此类推。
 */
public class Test {

    public static void main(String[] args) {
        Approver approver = new GroupLeader();
        approver.setNextApprover(new Manager()).setNextApprover(new Ceo());
        approver.approve(20000);
    }
}
