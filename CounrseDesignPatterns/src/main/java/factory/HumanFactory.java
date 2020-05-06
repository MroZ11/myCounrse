package factory;

/**
 * Created by cloud on 2019/12/23.
 */
public class HumanFactory extends UnitFactory {

    @Override
    protected Unit getLowUnit() {
        return new HumanCivilian();
    }

    @Override
    protected Unit getMidUnit() {
        return new HumanWarrior();
    }

    @Override
    protected Unit getHighUnit() {
        return new HumanCommander();
    }

}
