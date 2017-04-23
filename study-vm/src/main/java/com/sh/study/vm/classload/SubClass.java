package com.sh.study.vm.classload;

/**
 * Created by admin on 2017/4/22.
 */
public class SubClass extends ConstClass {
    static {
        System.out.println("SubClass init!!!");
    }
}
