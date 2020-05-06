package simple;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by cloud on 2020/4/11.
 */
public class StreamsOps {

    List<Integer> userids = Arrays.asList(1, 2, 3, 4);

    public static void main(String[] args) {
        StreamsOps streamsOps = new StreamsOps();
        streamsOps.compareWithSimple();
        streamsOps.otherOps();
    }


    /**
     * 对比传统的遍历方式，流方式可读性更高，代码也更简洁,对多层循环更友好
     */
    public void compareWithSimple() {
        long min2Count = 0;
        for (Integer integer : userids) {
            min2Count += integer >= 2 ? 1 : 0;
        }
        System.out.println(min2Count);

        long max3Count = userids.stream().filter(integer -> integer <= 3).count();
        System.out.println(max3Count);

        //另外需要注意filter是惰性取值，在未执行迫切方法是不会真正执行的
        userids.stream().filter(integer -> {
            System.out.println("only filter");
            return integer <= 3;
        });

        //如果是这样就会执行System.out.println();
        userids.stream().filter(integer -> {
            System.out.println("with count");
            return integer <= 3;
        }).count();


        /**
         * 关于惰性和及早：参见《Java8函数式编程》page.18
         * '''
         * 判断一个操作是惰性求值还是及早求值很简单：只需看它的返回值。
         * 如果返回值是Stream，那么是惰性求值；如果返回值是另一个值或为空，那么就是及早求值。
         * 使用这些操作的理想方式就是形成一个惰性求值的链，最后用一个及早求值的操作返回想要的结果，这正是它的合理之处。
         * 计数的示例也是这样运行的，但这只是最简单的情况：只含两步操作。
         * '''
         * 简单理解即返回Stream的操作都是在构建流
         *
         */
    }


    public void otherOps() {
        //Collectors.toList() 将结果转换到list中，使用filter筛选出符合条件的数据
        List<Integer> collect = userids.stream().filter(integer -> integer == 1).collect(toList());
        //Collectors.toSet() 将结果转换到Set中，使用filter筛选出符合条件的数据
        Set<Integer> set = userids.stream().filter(integer -> integer == 1).collect(toSet());

        //使用map可以把元数据进行转化
        List<Integer> time10collect = userids.stream().map(integer -> integer * 10).collect(toList());

        //使用flatMap将流映射到新的流上
        List<Integer> userids2 = Arrays.asList(5, 6, 7, 8);
        List<Integer> together = Stream.of(userids, userids2).flatMap(numbers -> numbers.stream()).collect(toList());

        //使用max和min求最大最小
        Integer min = together.stream().min(Integer::compareTo).get();
        Integer max = together.stream().max(Integer::compareTo).get();
        System.out.printf("min:%s,max:%s\n",min,max);

        //甚至比较对象
        List<User> users = new ArrayList<>();
        users.add(new User(18,"张三"));
        users.add(new User(55,"李四"));
        users.add(new User(78,"王五"));
        User young = users.stream().min(Comparator.comparing(user -> user.age)).get();
        User old = users.stream().max(Comparator.comparing(user -> user.age)).get();
        System.out.printf("young:%s,old:%s\n",young.name,old.name);

        //reduce操作可以实现从一组值中生成一个值,count、min和max都是reduce操作
        Integer total = together.stream().reduce((integer, integer2) -> integer + integer2).get();
        System.out.printf("plus all userid :%s\n",total);
        Integer totalAge = users.stream().map(User::getAge).reduce((integer, integer2) -> integer + integer2).get();
        System.out.printf("totoAge :%s\n",totalAge);

        //对于基础类型可用特殊方法减少拆箱装箱消耗，详情参见《Java8函数式编程》page.37
        IntSummaryStatistics statistics = users.stream().mapToInt(User::getAge).summaryStatistics();
        System.out.printf("Max:%d,Min:%d,Ave:%f,Sum:%d\n",statistics.getMax(),statistics.getMin(),statistics.getAverage(),statistics.getSum());

    }

    class User {
        int age;
        String name;

        public int getAge() {
            return age;
        }

        public User(int age, String name) {
            this.age = age;
            this.name = name;
        }
    }


}
