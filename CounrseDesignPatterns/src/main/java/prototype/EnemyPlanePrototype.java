package prototype;

/**
 * Created by cloud on 2019/12/19.
 */
public class EnemyPlanePrototype implements Cloneable{

    private int x,y=0;

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void fly(){
        y++;
    }
    //开放setX功能 给克隆对象修改X坐标
    public void setX(int x) {
        this.x = x;
    }

    public EnemyPlanePrototype(int x) {
        this.x = x;
    }

    @Override
    protected EnemyPlanePrototype clone() throws CloneNotSupportedException {
        return (EnemyPlanePrototype)super.clone();
    }
}
