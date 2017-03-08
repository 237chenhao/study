package com.ch.study.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by chenhao on 2017/2/22.
 */
public class HorseRace {
    static final int finish_line=75;
    private List<Horse> horsesList = new ArrayList<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private CyclicBarrier cyclicBarrier;
    public HorseRace(int horses,final int pause){
        cyclicBarrier = new CyclicBarrier(horses, new Runnable() {
            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder();
                for(int i=0;i<finish_line;i++){
                    stringBuilder.append("=");
                }
                System.out.println(stringBuilder);
                for (Horse horse : horsesList){
                    System.out.println(horse.tracks());
                }
                for (Horse horse : horsesList){
                    if(horse.getStrides()>=finish_line){
                        System.out.println(horse+" won!");
                        executorService.shutdownNow();
                        return;
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(pause);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        for(int i=0;i<horses;i++){
            Horse horse = new Horse(cyclicBarrier);
            horsesList.add(horse);
            executorService.execute(horse);
        }
    }

    public static void main(String[] args) {
        new HorseRace(7,200);
    }
}

class Horse implements Runnable{
    private static int counter=0;
    private final int id=counter++;
    private int strides=0;
    private static Random random = new Random(47);
    private static CyclicBarrier cyclicBarrier;
    public Horse(CyclicBarrier cyclicBarrier){
        Horse.cyclicBarrier = cyclicBarrier;
    }
    public synchronized int getStrides(){
        return strides;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                synchronized (this){
                    strides+=random.nextInt(3);
                }
                System.out.println(this+" await()");
                cyclicBarrier.await();
            }
        } catch (InterruptedException e) {
//            e.printStackTrace();
        } catch (BrokenBarrierException e) {
//            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Horse "+id+" ";
    }
    public String tracks(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<getStrides();i++){
            stringBuilder.append("*");
        }
        stringBuilder.append(id);
        return stringBuilder.toString();
    }
}
