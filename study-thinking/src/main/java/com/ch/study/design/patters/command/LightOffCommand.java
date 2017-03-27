package com.ch.study.design.patters.command;

/**
 * Created by chuangjiangx on 2017/3/14.
 */
public class LightOffCommand implements Command{
    private Light light;
    public LightOffCommand(Light light){
        this.light = light;
    }
    @Override
    public void evecute() {
        light.off();
    }
}
