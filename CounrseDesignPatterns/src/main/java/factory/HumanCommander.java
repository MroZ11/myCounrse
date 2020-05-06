package factory;

/**
 * Created by cloud on 2019/12/23.
 */
public class HumanCommander extends Unit{

    UnitLevel unitLevel = UnitLevel.HIGH;
    String name = "指挥官";
    String race = "人类";

    @Override
    protected int attack() {
        System.out.println(String.format( "%s %s 攻击，伤害15",this.race,this.name));
        return 15;
    }
}
