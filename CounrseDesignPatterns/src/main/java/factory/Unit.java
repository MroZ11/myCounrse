package factory;

/**
 * Created by cloud on 2019/12/23.
 */
public abstract class Unit {

    public static enum UnitLevel{
        LOW,MID,HIGH
    }

    protected UnitLevel unitLevel;

    String race;

    String Name;

    int blood;

    protected abstract int attack();

}
