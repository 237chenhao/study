package com.sh.study.vm.exec;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * Created by chuangjiangx-chenhao on 2017/5/8.
 */
public class Test {
    class GrandFather{
        void thinking(){
            System.out.println("i am grandfather");
        }

    }

    class Father extends GrandFather{
        @Override
        void thinking() {
            System.out.println("i am father");
        }
    }
    class Son extends Father{
        @Override
        void thinking() {
            System.out.println("i am son");
            MethodType methodType = MethodType.methodType(void.class);
            MethodHandle methodHandle = null;
            try {
                methodHandle = MethodHandles.lookup()
                        .findSpecial(GrandFather.class,"thinking",methodType,this.getClass());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            try {
                methodHandle.invoke(this);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Son son = new Test().new Son();
        GrandFather grandFather = son;
        grandFather.thinking();
    }
}
