package proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by cloud on 2019/12/23.
 */
public class Test {

    public static void main(String[] args) {

        //JDK动态代理 需要被代理类实现某一接口
        System.out.println("-------------JDK动态代理--(需要被代理类实现某一接口)-------");
        NamedPerson p1 = (NamedPerson) Proxy.newProxyInstance(PerImp.class.getClassLoader(), PerImp.class.getInterfaces(), (proxy, method, args2) -> {
            System.out.println("P1 DO before");

            Object obj = method.invoke(new PerImp(), args2);

            System.out.println("P1 DO after");
            return obj;
        });

        PerImp son = new PerImp();
        NamedPerson p2 = (NamedPerson) Proxy.newProxyInstance(son.getClass().getClassLoader(), son.getClass().getInterfaces(), (proxy, method, args2) -> {
            System.out.println("P2 DO before");

            Object obj = method.invoke(son, args2);

            System.out.println("P2 DO before");
            return obj;
        });
        p1.getName();
        System.out.println("-----");
        p2.getAge();

        //CGLib动态代理
        System.out.println("-------------CGLib动态代理--(不需要被代理类实现某一接口)-------");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PerImp.Student.class);
        // 设置回调方法
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("Student DO before ");
                Object result = methodProxy.invokeSuper(o, args);
                System.out.println("Student DO after");
                return result;
            }
        });
        PerImp.Student s1 = (PerImp.Student) enhancer.create();
        PerImp.Student s2 = (PerImp.Student) enhancer.create();
        s1.read();
        System.out.println("-----");
        s2.read();

    }
}
