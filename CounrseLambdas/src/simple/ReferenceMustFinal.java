package simple;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * 引用值应该是final的
 */
public class ReferenceMustFinal {

    public static void main(String[] args) {

        /*
        * 被引用值会默认编译为final
        * 等同于 final Long weight = 5L;
        * */
        int weight = 5;
        //如果再给final赋值编译会报错
        BinaryOperator<Integer> addWithWeight  = (Integer x, Integer y) -> {
            return x+y+weight;
        };

        /**
         * final是引用不可变,不代表引用对象的内容不可变
         */
        List<Integer> relsutTemps = new ArrayList<>();
        BinaryOperator<Integer> addAndAppendToTepms  = (Integer x, Integer y) -> {
            int resulst = x+y+weight;
            relsutTemps.add(resulst);
            return resulst;
        };

        addAndAppendToTepms.apply(3,5);
        System.out.println(relsutTemps.get(0));



    }

}
