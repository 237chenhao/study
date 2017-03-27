package com.sh.study.vm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by chuangjiangx-chenhao on 2017/3/26.
 * -Xmx20M -XX:MaxDirectMemorySize=10M
 */
public class DirectMemoryOOM {
    private static final int _1MB = 1024*1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field f = Unsafe.class.getDeclaredFields()[0];
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        while(true){
            unsafe.allocateMemory(_1MB);
        }
    }
}
