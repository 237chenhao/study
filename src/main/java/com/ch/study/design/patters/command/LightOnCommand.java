package com.ch.study.design.patters.command;

/**
 * Created by chuangjiangx on 2017/3/14.
 */
public class LightOnCommand implements Command{
    private Light light;
    public LightOnCommand(Light light){
        this.light = light;
    }
    @Override
    public void evecute() {
        light.on();
    }
}
