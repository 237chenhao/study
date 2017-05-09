package com.sh.study.vm.exec;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by chuangjiangx-chenhao on 2017/5/9.
 */
public class DynamicProxyTest {
    interface IHello{
        void sayHello();
    }
    static class Hello implements IHello{

        @Override
        public void sayHello() {
            System.out.println("hello word!");
        }
    }
    static class DynamicProxy implements InvocationHandler{
        private Object object;

        Object bind(Object obj){
            this.object = obj;
            return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("welcome");

            return method.invoke(object,args);
        }
    }

    public static void main(String[] args) {
        IHello hello = (IHello) new DynamicProxy().bind(new Hello());
        hello.sayHello();
    }
}
