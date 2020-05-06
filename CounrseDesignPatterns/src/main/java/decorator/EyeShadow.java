package decorator;

/**
 * Created by cloud on 2019/12/20.
 */
public class EyeShadow extends Beautify{

    public EyeShadow(ShowAble showAble) {
        super(showAble);
    }

    @Override
    public void show() {
        super.show();
        setEyeShadow(showAble);
    }

    private void setEyeShadow(ShowAble showAble){
        System.out.println("——>带上眼影");
    }

}
