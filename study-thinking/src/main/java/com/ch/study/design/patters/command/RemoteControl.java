package com.ch.study.design.patters.command;

/**
 * Created by chuangjiangx-chenhao on 2017/3/25.
 */
public class RemoteControl {
    Command[] onCommands = new Command[7];
    Command[] offCommands = new Command[7];

    public RemoteControl(int solt,Command onCommands, Command offCommands) {
        this.onCommands[solt] = onCommands;
        this.offCommands[solt] = offCommands;
    }
    public void on(int solt){
        onCommands[solt].evecute();
    }

    public void off(int solt){
        onCommands[solt].evecute();
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(5<<3);
        stringBuffer.append("\n----------remote control---------\n");
        for(int i=0,len=onCommands.length;i<len;i++){
            stringBuffer.append("[solt"+i+"] ").append(onCommands[i].getClass().getName())
                    .append("\t").append(offCommands[i].getClass().getName())
                    .append("\n");
        }
        return stringBuffer.toString();
    }
}
