package decorator;

/**
 * 装饰器模式（Decorator Pattern）
 *
 * 对装饰器模式来说，装饰者（decorator）和被装饰者（decoratee）都实现同一个 接口。
 *
 * <p>
 * 动态地给一个对象添加一些额外的职责。就增加功能来说，装饰器模式相比生成子类更为灵活。
 * <p>
 * 主要解决：一般的，我们为了扩展一个类经常使用继承方式实现，由于继承为类引入静态特征，并且随着扩展功能的增多，子类会很膨胀。
 * <p>
 * 何时使用：在不想增加很多子类的情况下扩展类。
 * <p>
 * 装饰器和代理模式区别：
 * https://www.cnblogs.com/xiaolovewei/p/7751332.html
 */
public class Test {


    public static void main(String[] args) {
        Lipstick lipstick = new Lipstick(new Girl());
        EyeShadow eyeShadow = new EyeShadow(lipstick);
        eyeShadow.show();
    }


}
