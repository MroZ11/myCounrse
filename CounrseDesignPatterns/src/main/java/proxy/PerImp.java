package proxy;

/**
 * Created by cloud on 2019/10/31.
 */
public class PerImp implements NamedPerson {


    @Override
    public String getName() {
        System.out.println("getName");
        return null;
    }

    @Override
    public Integer getAge() {
        System.out.println("getAge");
        return null;
    }

    static class Student {

        public void read() {
            System.out.println("read");
        }

    }
}
