package com.ch.study.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by chenhao on 2017/2/23.
 */
public class DelayQueueDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        DelayQueue<DelayedTask> delayedTasks = new DelayQueue<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for(int i=0;i<20;i++){
            delayedTasks.put(new DelayedTask(random.nextInt(5000)));
        }
        delayedTasks.add(new DelayedTask.EndSentinel(5000,executorService));
        executorService.execute(new DelayedTaskConsumer(delayedTasks));
    }
}

class DelayedTask implements Runnable,Delayed{
    private static int counter = 0;
    private final int id=counter++;
    private final int delta;
    private final long trigger;
    protected static List<DelayedTask> sequence = new ArrayList<>();
    public DelayedTask(int delayInMilliseconds){
        delta = delayInMilliseconds;
        trigger = System.nanoTime()+ TimeUnit.NANOSECONDS.convert(delta,TimeUnit.MILLISECONDS);
        sequence.add(this);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger-System.nanoTime(),TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayedTask delayedTask = (DelayedTask) o;
        if(this.trigger>delayedTask.trigger){
            return 1;
        }
        if(this.trigger<delayedTask.trigger){
            return -1;
        }
        return 0;
    }

    @Override
    public void run() {
        System.out.println(this+" ");
    }

    @Override
    public String toString() {
        return String.format("[%1$-4s]",delta)+" Task "+id;
    }
    public String summary(){
        return  "("+id+":"+delta+")";
    }
    public static class EndSentinel extends DelayedTask{
        private ExecutorService executorService;
        public EndSentinel(int delayInMilliseconds,ExecutorService executorService) {
            super(delayInMilliseconds);
            this.executorService = executorService;
        }
        @Override
        public void run(){
            for(DelayedTask dt : sequence){
                System.out.println(dt.summary()+" ");
            }
            System.out.println();
            System.out.println(this+" Calling shutdownNow");
            executorService.shutdownNow();
        }
    }
}

class DelayedTaskConsumer implements Runnable{
    private DelayQueue<DelayedTask> q;
    public DelayedTaskConsumer(DelayQueue<DelayedTask> q){
        this.q=q;
    }
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                q.take().run();
            }
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
        System.out.println("finished DelayedTaskConsumer");
    }
}