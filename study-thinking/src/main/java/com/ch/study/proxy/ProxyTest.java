package com.ch.study.proxy;

import org.springframework.cglib.proxy.Enhancer;

/**
 * Created by chuangjiangx-chenhao on 2017/3/27.
 */
public class ProxyTest {

    public static void main(String[] args) {
        Programmer programmer = new Programmer();
        Hacker hacker = new Hacker();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(programmer.getClass());

        enhancer.setCallback(hacker);

        Programmer proxy = (Programmer) enhancer.create();
        proxy.code();


    }
}
