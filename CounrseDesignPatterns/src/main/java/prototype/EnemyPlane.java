package prototype;

/**
 * 敌机类
 */
public class EnemyPlane {

    private int x,y=0;

    public EnemyPlane(int x) {
        this.x = x;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void fly(){
        y++;
    }


}
