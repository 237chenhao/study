package com.ch.study.design.patters.command;

/**
 * Created by chuangjiangx-chenhao on 2017/3/25.
 * 音响类
 */
public class Stereo {

    public void on(){
        System.out.println("打开音响");
    }

    public void setCD(){
        System.out.println("设置CD。。。");
    }
    public void setVolume(int volume){
        System.out.println("调整音量到："+volume);
    }
}
