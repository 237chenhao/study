package com.sh.study.vm;

/**
 * Created by chuangjiangx-chenhao on 2017/3/26.
 * VM Args:-Xss2M 设置栈内存容量
 */
public class JavaVMStackOOM {
    private int threadCount=0;
    private void dontStop(){
        while(true){}
    }



    public void stackLeakByThread(){
        while(true){
            ++threadCount;
            if(((64-1)&threadCount )==0){
                System.out.println(threadCount);
            }
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();

        }


    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();

//        for(int i=0;i<100;i++){
//            System.out.println("(16-1)&"+i+":"+((16-1)&i));
//        }
    }
}
