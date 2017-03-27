package com.sh.study.vm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chuangjiangx-chenhao on 2017/3/26.
 * -
 * VM Args:-XX:PermSize=10M -XX:MaxPermSize=10M
 */
public class RumtimeConstantPoolOOM {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int i=0;
        while(true){
            list.add(String.valueOf(i++).intern());
        }
    }
}
