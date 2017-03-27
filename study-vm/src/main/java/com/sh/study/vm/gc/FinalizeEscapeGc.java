package com.sh.study.vm.gc;

import java.util.concurrent.TimeUnit;

/**
 * Created by chuangjiangx-chenhao on 2017/3/26.
 * 对象可在被Gc时自我拯救
 */
public class FinalizeEscapeGc {
    private static FinalizeEscapeGc SAVE_HOOK=null;
    public void isAlive(){
        System.out.println("我活着");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("执行了finalize()方法");
        SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGc();

        //对象第一次成功拯救自己
        SAVE_HOOK = null;
        System.gc();
        //因为finalize()方法优先级很低，所以暂停0.5秒以等待它
        TimeUnit.SECONDS.sleep(1);
        if(SAVE_HOOK !=null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("被回收了");
        }

        //失败了
        SAVE_HOOK = null;
        System.gc();
        //因为finalize()方法优先级很低，所以暂停0.5秒以等待它
        TimeUnit.SECONDS.sleep(1);
        if(SAVE_HOOK !=null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("被回收了");
        }
    }
}
