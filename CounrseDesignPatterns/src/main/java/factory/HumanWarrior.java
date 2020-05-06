package factory;

/**
 * Created by cloud on 2019/12/23.
 */
public class HumanWarrior extends Unit{

    UnitLevel unitLevel = UnitLevel.MID;
    String name = "战士";
    String race = "人类";

    @Override
    protected int attack() {
        System.out.println(String.format( "%s %s 攻击，伤害5",this.race,this.name));
        return 5;
    }
}
