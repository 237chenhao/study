package com.ch.study.thread;

/**
 * Created by chenhao on 2017/2/13.
 */
public class Joining {
    public static void main(String[] args) {
        Sleeper sleeper1 = new Sleeper("sleeper1",1500),
                sleeper2 = new Sleeper("sleeper2",1500);
        Joiner joiner1 = new Joiner("joiner1",sleeper1),
                joiner2 = new Joiner("joiner2",sleeper2);
        sleeper2.interrupt();
    }
}

class  Sleeper extends Thread{
    private int duration;
    public Sleeper(String name,int duration){
        super(name);
        this.duration = duration;
        start();
    }

    @Override
    public void run() {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            System.out.println(getName()+" was interrupted. is interrupted():" + interrupted() );
            return;
        }
        System.out.println(getName()+" has awakened");
    }
}

class Joiner extends Thread{
    private Sleeper sleeper;
    public Joiner(String name,Sleeper sleeper){
        super(name);
        this.sleeper = sleeper;
        start();
    }

    @Override
    public void run() {
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getName()+" join completed");
    }
}
