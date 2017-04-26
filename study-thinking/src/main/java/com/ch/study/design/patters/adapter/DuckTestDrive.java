package com.ch.study.design.patters.adapter;

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

    }
    static void testDuck(Duck duck){
        duck.quack();
        duck.fly();
    }
}
