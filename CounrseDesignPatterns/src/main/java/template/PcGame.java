package template;

/**
 * 定义一个操作中的算法的骨架，而将一些步骤延迟到子类中。
 * 模板方法使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。
 */
public class PcGame extends Game{
    @Override
    void initialize() {
        System.out.println("init PC GAME");
    }

    @Override
    void startPlay() {
        System.out.println("start PC GAME");
    }

    @Override
    void endPlay() {
        System.out.println("end PC GAME");
    }

    public static void main(String[] args) {
        PcGame pcGame = new PcGame();
        pcGame.play();
    }
}
