package strategy;

/**
 * Created by cloud on 2019/12/19.
 */
public class Subtraction implements Strategy {
    @Override
    public double calculate(double num1, double num2) {
        return  num1-num2;
    }
}
