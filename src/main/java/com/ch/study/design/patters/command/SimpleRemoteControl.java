package com.ch.study.design.patters.command;

/**
 * Created by chuangjiangx on 2017/3/14.
 */
public class SimpleRemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void buttonWasPressed(){
        command.evecute();
    }
}
