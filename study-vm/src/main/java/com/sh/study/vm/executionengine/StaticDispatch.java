package com.sh.study.vm.executionengine;

/**
 * Created by chuangjiangx-chenhao on 2017/4/28.
 * 方法静态方法演示
 * 静态分派的典型是方法重载
 */
public class StaticDispatch {
    static abstract class Human{}
    static class Mam extends Human{}
    static class Womam extends Human{}

    public void say(Human human){
        System.out.println("human");
    }
    public void say(Mam mam){
        System.out.println("man");
    }
    public void say(Womam womam){
        System.out.println("womam");
    }

    public static void main(String[] args) {
        Human mam = new Mam();
        Human womam = new Womam();
        StaticDispatch staticDispatch = new StaticDispatch();

        staticDispatch.say((Mam)mam);
        staticDispatch.say((Womam)womam);

        System.out.println(String.valueOf(Float.MAX_VALUE));
        System.out.println(Double.MAX_VALUE);
    }
}
