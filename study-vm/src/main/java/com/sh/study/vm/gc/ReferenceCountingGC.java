package com.sh.study.vm.gc;

/**
 * Created by chuangjiangx-chenhao on 2017/3/26.
 */
public class ReferenceCountingGC {
    public Object instance = null;
    private static final int _1MB = 1024*1024;
    //占点内存，以便能在GC日志中看清楚是否被回收过
    private byte[] bigSize = new byte[2*_1MB];

    public static void testGC(){
            ReferenceCountingGC a = new ReferenceCountingGC();
            ReferenceCountingGC b = new ReferenceCountingGC();
            a.instance =b;
            b.instance = a;
        a=null;
        b=null;

        //假设在这行发生Gc，a和b是否能被回收？
        System.gc();
    }

    public static void main(String[] args) {
        testGC();
    }
}
