package factory;

/**
 * Created by cloud on 2019/12/23.
 */
public abstract class UnitFactory {

    protected abstract Unit getLowUnit();
    protected abstract Unit getMidUnit();
    protected abstract Unit getHighUnit();

}
