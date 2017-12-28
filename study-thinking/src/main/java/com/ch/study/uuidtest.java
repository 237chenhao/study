package com.ch.study;

import java.util.*;

/**
 * @author cj-ch
 * @date 2017/12/14 下午3:33
 */
public class uuidtest {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>(10*1024);
        int count = 50;
        while(--count>0){
            new Thread(() -> {
                for(int i=0;i<500;i++){
                    String s = UUID.randomUUID().toString();
                    if(!set.add(s)){
                        System.out.println(s);
                    }
                }
            }).start();
        }
    }
}
