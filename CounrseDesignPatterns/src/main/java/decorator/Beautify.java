package decorator;

/**
 * 注意 这里用抽象类,在其子类中进行扩展
 */
public abstract class Beautify implements ShowAble{

    protected ShowAble showAble;

    public Beautify(ShowAble showAble) {
        this.showAble = showAble;
    }

    @Override
    public void show() {
        showAble.show();
    }
}
