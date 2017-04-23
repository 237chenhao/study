package com.sh.study.vm.classload;

/**
 * Created by admin on 2017/4/22.
 */
public class ConstClass {

    static {
        System.out.println("ConstClass init!!");
    }
    public static final String HELLOWORD = "世界你好";
    public static final void h(){
        System.out.println(HELLOWORD);
    }
}
