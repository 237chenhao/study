package com.ch.study.classload;

/**
 * Created by chuangjiangx-chenhao on 2017/4/24.
 */
public class Sub extends Parent {
    public static int B=A;
    static {
        System.out.println("sub");
        A=3;
    }
    public static void main(String[] args) {
        System.out.println(Sub.B);
        System.out.println(Sub.B);
    }
}
