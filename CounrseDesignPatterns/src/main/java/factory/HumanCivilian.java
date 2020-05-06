package factory;

/**
 * Created by cloud on 2019/12/23.
 */
public class HumanCivilian extends Unit{

    UnitLevel unitLevel = UnitLevel.LOW;
    String name = "平民";
    String race = "人类";

    @Override
    protected int attack() {
        System.out.println(String.format( "%s %s 攻击，伤害1",this.race,this.name));
        return 15;
    }
}
