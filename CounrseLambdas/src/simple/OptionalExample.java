package simple;

import java.util.Optional;

/**
 * Optional用于产生一个可为空的对象 避免NullPointerException
 * <p>
 * <p>
 * '''
 * 使用Optional对象有两个目的：
 * 首先，Optional对象鼓励程序员适时检查变量是否为空，以避免代码缺陷；
 * 其次，它将一个类的API中可能为空的值文档化，这比阅读实现代码要简单很多。
 * <p>
 * 参见《Java8函数式编程》page47
 * '''
 * </p>
 */
public class OptionalExample {

    public static void main(String[] args) {
        OptionalExample optionalExample = new OptionalExample();
        optionalExample.createOptional();
    }

    private void createOptional() {
        //用Optional.of构建
        Optional<String> a = Optional.of("a");
        System.out.printf("isPresent:%s,value:%s\n", a.isPresent(), a.get());

        //用Optional.empty()创建一个空的Optional
        Optional<Object> empty = Optional.empty();
        //用Optional.ofNullable()创建一个可为空的Optional
        Optional<Object> nullable = Optional.ofNullable(null);

        //当为空时 调用empty.get()会抛出异常
        try {
            System.out.printf("isPresent:%s,value:%s\n", empty.isPresent(), empty.get());
            System.out.printf("isPresent:%s,value:%s\n", nullable.isPresent(), nullable.get());
        } catch (Exception e) {
            System.out.println("Exception->" + e.getMessage());
        }

        //用orElse或orElseGet来代替get,指定缺省值
        System.out.printf("isPresent:%s,value:%s\n", empty.isPresent(), empty.orElse("8"));
        System.out.printf("isPresent:%s,value:%s\n", nullable.isPresent(), nullable.orElseGet(Math::random));

        //用orElseThrow指定空值时的异常
        System.out.printf("isPresent:%s,value:%s\n", nullable.isPresent(), nullable.orElseThrow(() -> {
            return new RuntimeException("can not find val");
        }));

    }


}
