package com.ch.study.design.patters.command;

/**
 * Created by chuangjiangx-chenhao on 2017/3/25.
 * 让音响播放CD类
 */
public class StereoOnWithCDCommand implements Command{
    private Stereo stereo;

    public StereoOnWithCDCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    @Override
    public void evecute() {
        stereo.on();
        stereo.setCD();
        stereo.setVolume(11);
    }
}
