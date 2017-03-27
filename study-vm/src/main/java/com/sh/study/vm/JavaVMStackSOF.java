package com.sh.study.vm;

/**
 * Created by chuangjiangx-chenhao on 2017/3/26.
 * VM Args:-Xss128k 设置栈内存容量
 */
public class JavaVMStackSOF {
    private int stackLength = -1;
    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF javaVMStackSOF = new JavaVMStackSOF();

        try {
            javaVMStackSOF.stackLeak();
        } catch (Exception e) {
            System.out.println("stack Length:"+javaVMStackSOF.stackLength);
            throw  e;
        }

    }
}
