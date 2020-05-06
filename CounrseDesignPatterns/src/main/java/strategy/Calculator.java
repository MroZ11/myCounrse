package strategy;

/**
 * 总结  Calculator相当于宿主对象，他拥有一个策略(接口属性)，当指定该接口属性时，在运行其方法时，
 * 就采用具体的实现类的策略，执行。该策略是可随意随意插拔的，比如这里的加法和减法。
 */
public class  Calculator {
    private Strategy strategy;

    public Calculator(Strategy strategy) {
        this.strategy = strategy;
    }

    public double doCalculate(double num1,double num2){
        return this.strategy.calculate(num1,num2);
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator(new Addition());
        System.out.println(calculator.doCalculate(1.2,1.3));
        calculator.setStrategy(new Subtraction());
        System.out.println(calculator.doCalculate(1.2,1.3));
    }

}
