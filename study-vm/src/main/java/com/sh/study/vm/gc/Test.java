package com.sh.study.vm.gc;

/**
 * Created by chuangjiangx-chenhao on 2017/4/5.
 */
public class Test {
    private static final int _1MB = 1024*1024;
    public static void main(String[] args) {
        byte[] b1,b2,b3;
        b1 = new byte[_1MB/4];
        //什么时候进入老年代取决于XX:MaxTenuringThreshold设置
        b2 = new byte[4*_1MB];
        b3 = new byte[4*_1MB];
        b3 = null;
        b3 = new byte[4*_1MB];
    }
}
