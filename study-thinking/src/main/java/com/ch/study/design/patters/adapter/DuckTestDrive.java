package com.ch.study.design.patters.adapter;

import java.util.Arrays;

/**
 * Created by admin on 2017/4/26.
 */
public class DuckTestDrive {
    public static void main(String[] args) {
        MallardDuck mallardDuck = new MallardDuck();
        WildTurkey wildTurkey = new WildTurkey();

        TurkeyAdapter turkeyAdapter = new TurkeyAdapter(wildTurkey);

        System.out.println("火鸡");
        wildTurkey.gobble();
        wildTurkey.fly();

        System.out.println("鸭子");
        testDuck(mallardDuck);

        System.out.println("假装成鸭子的火鸡");
        testDuck(turkeyAdapter);
        char a='a',A='A';
        int x=Integer.MAX_VALUE;
        int m= Integer.MIN_VALUE;
        System.out.println("0"+Integer.toBinaryString(x));
        System.out.println(Integer.toBinaryString(m));
        System.out.println(Integer.toBinaryString(-2));
        System.out.println(Integer.toBinaryString(-1000));
//        System.out.println(Integer.parseInt("10",2));
        int[] as= {1,2,3,4,5};
        exchange(as,5);
        System.out.println(Arrays.toString(as));
    }
    static void testDuck(Duck duck){
        duck.quack();
        duck.fly();


    }
    static void exchange(int[] a,int len){
        int l=len-1;
        for(int i=0;i<l;i++,l--){
            a[i] ^= a[l];
            a[l] ^= a[i];
            a[i] ^= a[l];
        }
    }
}
