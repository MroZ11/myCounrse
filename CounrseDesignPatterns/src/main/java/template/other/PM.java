package template.other;

/**
 * Created by cloud on 2019/12/19.
 */
public interface PM {

    void analyze();//需求分析

    void design();//设计

    void develop();//开发

    boolean test();//测试

    void release();//发布

    default void kickoff(){
        analyze();
        design();
        do {
            develop();
        } while (!test());
        release();
    }
}
