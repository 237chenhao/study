package com.sh.study.vm.exec;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Created by chuangjiangx-chenhao on 2017/5/4.
 */
public class MethodHandleTest {
    static class ClassA{
        public void println(String str){
            System.out.println("ClassA:"+str);
        }
    }
    private static MethodHandle getPrintlnMH(Object obj) throws NoSuchMethodException, IllegalAccessException {
        MethodType methodType = MethodType.methodType(void.class,String.class);
        return MethodHandles.lookup()
                .findVirtual(obj.getClass(),"println",methodType)
                .bindTo(obj);
    }

    public static void main(String[] args) throws Throwable {
        Long l =  System.currentTimeMillis();
        Object obj = (l&1)==0 ? System.out : new ClassA();
        System.out.println(obj);
        getPrintlnMH(obj).invokeExact("icy要轻有fenix");
    }
}
