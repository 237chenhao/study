package com.ch.study.design.patters.adapter;

/**
 * Created by admin on 2017/4/26.
 */
public class WildTurkey implements  Turkey {
    @Override
    public void gobble() {
        System.out.println("咯咯叫");
    }

    @Override
    public void fly() {
        System.out.println("我只能飞很短距离");
    }
}
