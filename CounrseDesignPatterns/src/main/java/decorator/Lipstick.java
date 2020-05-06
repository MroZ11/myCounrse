package decorator;

/**
 * Created by cloud on 2019/12/20.
 */
public class Lipstick extends  Beautify{

    public Lipstick(ShowAble showAble) {
        super(showAble);
    }

    @Override
    public void show() {
        super.show();
        setLipstick(showAble);
    }

    private void setLipstick(ShowAble showAble){
        System.out.println("——>涂上口红");
    }

}
