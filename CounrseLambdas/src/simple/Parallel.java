package simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <h1>并行提高处理效率</h1>
 *
 * <p>
 *     '''
 *     输入流的大小并不是决定并行化是否会带来速度提升的唯一因素，
 *     性能还会受到编写代码的方式和核的数量的影响。
 *     ---------参见《Java8函数式编程》page.71
 *     '''
 * </p>
 *
 *
 */
public class Parallel {

    public static void main(String[] args) {
        Parallel parallel = new Parallel();
        parallel.simple();
        parallel.parallelInintArray();
    }

    /**
     * 使用parallelStream提供并行流处理，在大量数据下体用性能优化
     */
    public void simple() {
        List<Long> strings = new ArrayList<>();


        //通过limit限制次数
        /*Stream.iterate(0, i -> i + 1).limit(10000).forEach(i -> {
            strings.add(Long.valueOf(i));
        });*/
        //用IntStream代替for i 循环
        IntStream.range(0,10000).forEach(i -> {
            strings.add(Long.valueOf(i));
        });


        long l1 = System.currentTimeMillis();
        System.out.println(strings.stream().collect(Collectors.summarizingLong(Long::valueOf)));


        long l2 = System.currentTimeMillis();
        System.out.println(l2 - l1);


        System.out.println(strings.parallelStream().collect(Collectors.summarizingLong(Long::valueOf)));
        long l3 = System.currentTimeMillis();
        System.out.println(l3 - l2);


    }

    /**
     *使用parallelSetAll并行初始化数组
     */
    public void parallelInintArray() {

        //使用parallelSetAll并行初始化数组 相当于
        //IntStream.range(0, array.length).parallel().forEach(i -> { array[i] = generator.applyAsInt(i); });
        int[] values1= new int[50];
        Arrays.parallelSetAll(values1,i->i);
        System.out.println(Arrays.toString(values1));


        /**
         '''
         * Cumulates, in parallel, each element of the given array in place,
         * using the supplied function.
         * For example if the array initially holds [2, 1, 0, 3] and the operation performs addition,
         * then upon return the array holds [2, 3, 3, 6].
         * Parallel prefix computation is usually more efficient than sequential loops for large arrays.
         * '''
         *
         *  累加处理，values1[0,1,2,3,4,5,6.....49],left代表左侧累加,最后values1会变成[0,1,3,6,10,15....]
         */
        Arrays.parallelPrefix(values1,(left, right) -> left+right);
        System.out.println(Arrays.toString(values1));

    }


}
