package prototype;

/**
 * Created by cloud on 2019/12/19.
 */
public class EnemyPlaneFactory {

    private  static EnemyPlanePrototype enemyPlanePrototype = new EnemyPlanePrototype(200);

    public static EnemyPlanePrototype getInstance(int x) throws CloneNotSupportedException {

        EnemyPlanePrototype clone = enemyPlanePrototype.clone();//复制原型机
        clone.setX(x);
        return  clone;
    }

}
