package com.ch.study.design.patters.adapter;

/**
 * Created by admin on 2017/4/26.
 */
public class MallardDuck implements Duck {
    @Override
    public void quack() {
        System.out.println("呱呱叫");
    }

    @Override
    public void fly() {
        System.out.println("我在飞");
    }
}
