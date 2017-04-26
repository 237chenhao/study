package com.ch.study.design.patters.adapter;

/**
 * Created by admin on 2017/4/26.
 */
public class TurkeyAdapter implements Duck{
    private Turkey turkey;

    public TurkeyAdapter(Turkey turkey) {
        this.turkey = turkey;
    }

    @Override
    public void quack() {
        turkey.gobble();
    }

    @Override
    public void fly() {
        for(int i=0;i<5;i++){
            //需要飞五次才能达到要求
            turkey.fly();
        }
    }
}
