package simple;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by cloud on 2020/4/13.
 */
public class CollectorAndAdvanced {

    Person person1 = new Person("张三", 25);
    Person person2 = new Person("李四", 19);
    Person person3 = new Person("王五", 16);
    Person person4 = new Person("张三", 60);

    public static void main(String[] args) {
        CollectorAndAdvanced collectorAndAdvanced = new CollectorAndAdvanced();
        collectorAndAdvanced.getMethodReference();
        collectorAndAdvanced.collectHandler();
        collectorAndAdvanced.combinationCollector();
    }

    /**
     * 获取方法引用
     */
    public void getMethodReference() {
        Person person1 = new Person("张三", 25);
        Person person2 = new Person("李四", 19);
        List<Person> personList = Arrays.asList(person1, person2);

        //标准语法为Classname::methodName,可以将method转化为function函数
        personList.stream().mapToInt(Person::getAge).forEach(value -> {
            System.out.println(value);
        });


        //使用sorted排序
        /*
        可以直接把入参提供给函数消耗
        等同于
        personTreeSet.forEach(person -> {
            System.out.println(person);
        });*/
        personList.stream().sorted(Comparator.comparingInt(Person::getAge)).forEach(
                System.out::println
        );


    }

    /**
     * 通过collect将流处理结果进行收集
     */
    public void collectHandler() {

        List<Person> personList = Arrays.asList(person1, person2, person3, person4,person1);

        //使用toCollection，收集流中的每个元素，存放到对应Collection中
        HashSet<Person> personTreeSet = personList.stream().collect(Collectors.toCollection(HashSet::new));

        //使用maxBy调出最大值的元素
        Optional<Person> collect = personList.stream().collect(Collectors.maxBy(Comparator.comparingInt(Person::getAge)));
        System.out.println(collect.get());

        //使用averagingDouble求平均值
        Double aveAge = personList.stream().collect(Collectors.averagingDouble(Person::getAge));
        System.out.printf("aveAge:%s\n", aveAge);

        //可以通过summaryStatistics或取数值的统计信息
        IntSummaryStatistics statistics = personList.stream().mapToInt(Person::getAge).summaryStatistics();
        System.out.printf("Max:%d,Min:%d,Ave:%f,Sum:%d\n", statistics.getMax(), statistics.getMin(), statistics.getAverage(), statistics.getSum());

        /**
         * 分块操作，将流根据条件分成不同的块
         *  partitioningBy  分区 是否判断
         */
        Map<Boolean, List<Person>> adultIfMap = personList.stream().collect(Collectors.partitioningBy(person -> person.getAge() >= 18));
        adultIfMap.forEach((aBoolean, people) -> {
            if (aBoolean) {
                System.out.println("\nAdult Person:");
            } else {
                System.out.println("\nNo Adult Person:");
            }
            people.stream().forEach(System.out::println);
        });

        //groupingBy分组
        Map<Integer, List<Person>> listMap = personList.stream().collect(Collectors.groupingBy(o -> o.getAge()));
        listMap.forEach((integer, people) -> {
            System.out.println("\nAge Group:" + integer);
            people.stream().forEach(System.out::println);
        });

        //使用joining很好的处理字符串拼接
        String namesStr = personList.stream().map(Person::getName).collect(Collectors.joining(",", "(", ")"));
        System.out.printf("\nALL Names:\n%s \n", namesStr);

    }


    /**
     * 灵活使用组合收集器
     */
    public void combinationCollector(){
        List<Person> personList = Arrays.asList(person1, person2, person3, person4,person1);
        //不建议用 stream.distinct去重,特别是并行流处理中
        Set<Person> personSet = personList.stream().collect(Collectors.toSet());

        person1.setFriends(Arrays.asList(person3));
        person2.setFriends(Arrays.asList(person3,person4));
        person3.setFriends(Arrays.asList(person1, person2));
        person4.setFriends(Arrays.asList(person2));


        //按名称分组后 统计同名的人数
        Map<String, Long> nameCount = personSet.stream().collect(Collectors.groupingBy(Person::getName, Collectors.counting()));
        nameCount.forEach((s, aLong) -> {
            System.out.printf("Name:%s Count:%s\n", s, aLong);
        });

        //分组后计算累加所有分组的好友数目
        Map<String, Integer> nameFriendTotalCount = personSet.stream().collect(
                Collectors.groupingBy(person -> person.getName(), Collectors.summingInt(value ->
                                value.getFriends().size()
                        )
                ));

        nameFriendTotalCount.forEach((s, integer) -> {
            System.out.printf("Name:%s friendTotalCount:%s\n", s, integer);
        });

        //分组后列出所有好友
        Map<String, String> collectWithFriendNamesAll = personSet.stream().collect(
                Collectors.groupingBy(person -> person.getName(), Collectors.mapping(o ->
                             o.getFriends().stream().map(Person::getName).collect(Collectors.joining(",", "[", "]"))
                        , Collectors.joining(",", "[", "]"))
                ));
        collectWithFriendNamesAll.forEach((s1, s2) -> {
            System.out.println(s1+"all friends >"+s2);
        });

    }































    class Person {
        String name;
        int age;
        List<Person> friends;


        public Person() {
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<Person> getFriends() {
            return friends;
        }

        public void setFriends(List<Person> friends) {
            this.friends = friends;
        }

        public Stream<Person> getFriendsStrem() {
            return this.friends.stream();
        }


        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }


}
