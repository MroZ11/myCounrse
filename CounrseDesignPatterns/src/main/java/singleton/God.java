package singleton;

/**
 * 总结：单例模式即保证类的实例只存在一个，已达到全局使用，节约性能
 *
 */
public class God {
    private static God god;

    //私有化构造方法 不允许在其他类中new
    private  God() {
    }

    /**
     *  保证获取的God实例为同一个对象
     *  这里未加锁 如果并发执行时 同样会new多个
     * @return
     */
    private static God  getGod(){
        //延迟加载 在第一次请求时再加载  这里未加锁 如果并发执行时 同样会new多个
        //如果不在乎内存 也可以在直接在初始化 静态对象
        if(god==null){
            god = new God();
        }
        return god;
    }

    /**
     * 增加同步锁 在未生产出实例时需等待锁，而后再请求不需要等待了
     * @return
     */
    private static God  getGodConcurrent(){
        if (god == null) {
            synchronized(God.class){
                if (god == null) {
                    god = new God();
                }
            }
        }
        return god;
    }

}
