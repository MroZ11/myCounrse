package prototype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by cloud on 2019/12/19.
 * <p>
 * 总结:
 * <p>
 * 原型模式是利用clone来逃避 实例化时加载并初始化类和创建对象过程(注意如果是类中含有引用类型成员变量 请实现深度拷贝)
 *
 * <p>
 * 主要使用场景:
 * <p>
 * 1.在需要一个类的大量对象的时候，使用原型模式是最佳选择，因为原型模式是在内存中对这个对象进行拷贝，
 * 要比直接new这个对象性能要好很多，在这种情况下，需要的对象越多，原型模式体现出的优点越明显。
 * <p>
 * 2.如果一个对象的初始化需要很多其他对象的数据准备或其他资源的繁琐计算，那么可以使用原型模式。
 * <p>
 * 3.当需要一个对象的大量公共信息，少量字段进行个性化设置的时候，也可以使用原型模式拷贝出现有对象的副本进行加工处理。
 */
public class Client {


    public static void main(String[] args) throws CloneNotSupportedException {

        //未使用原型模式时 想要构建50架飞机 需要实例化50次
        List<EnemyPlane> enemyPlanes = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            EnemyPlane enemyPlane = new EnemyPlane(new Random().nextInt(200));
            enemyPlanes.add(enemyPlane);
        }


        //使用原型模式时 是根据已有对象 克隆出新对象
        List<EnemyPlanePrototype> enemyPlanePrototypes = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            EnemyPlanePrototype enemyPlanePrototype = EnemyPlaneFactory.getInstance(new Random().nextInt(200));
            enemyPlanePrototypes.add(enemyPlanePrototype);
        }


    }
}
