package test;

import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true) //可以使set方法返回对象本身,达到链式调用目的
@ToString
public class Snake {

    private int age;
    private String name;
    private int sex;

}
