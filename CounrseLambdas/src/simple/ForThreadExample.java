package simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

/**
 * 格式和示例
 */
public class ForThreadExample {

    public String[] canDoList = {"add", "plus", "remove", "cal"};

    public static void main(String[] args) {

        //传统 创建匿名类
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("run");
            }
        }).run();

        //lambda中  只需因为Runnable只有一个接口方法，只需要传递方法的实现即可
        new Thread(() -> {
            System.out.println("run2");
        }).run();

        //变种写法 当方法体中只是单行代码 甚至可以省略中括号
        new Thread(() ->
                System.out.println("run3")
        ).run();

        //带传入参和出参
        BinaryOperator<Long> add = (Long x, Long y) -> {
            return x + y;
        };

        //可以省略入参类型，甚至单行代码可直接作为返回值 不用写return;虽然简洁但是可读性变差
        BinaryOperator<Long> sub = (x, y) -> x - y;


        //lambda依赖于上下文环境，目标类型由编译器推断(非lambda引入的概念)
        //如数组初始化,等号右边的代码并没有声明类型，系统根据上下文推断出类型信息
        String[] strings = {"hello", "word"};
        Map<String, String> map = new HashMap<>();

        //java 提供一组核心函数位于 java.util.function
        ForThreadExample forThreadExample = new ForThreadExample();
        forThreadExample.fiterCando(s -> s.equals("add") || s.equals("remove"));
        for (String s : forThreadExample.canDoList) {
            System.out.println(s);
        }

        //Supplier 无入参，有出参
        ThreadLocal<String> stringThreadLocal = ThreadLocal.withInitial(() -> "3333");
        System.out.println(stringThreadLocal.get());


    }

    /**
     * java 提供一组核心函数位于 java.util.function
     * 把一个方法(函数)作为入参或出参称之为高级函数
     *
     * @param p
     * @return
     */
    public ForThreadExample fiterCando(Predicate<String> p) {
        List<String> reStrsList = new ArrayList<String>();

        for (int i = 0; i < canDoList.length; i++) {
            if (!p.test(canDoList[i])) {
                reStrsList.add(canDoList[i]);
            }
        }
        this.canDoList = reStrsList.toArray(new String[reStrsList.size()]);

        return this;
    }


}
