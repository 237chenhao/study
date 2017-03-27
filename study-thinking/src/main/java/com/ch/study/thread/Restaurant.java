package com.ch.study.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenhao on 2017/2/19.
 */
public class Restaurant {
    Meal meal;
    Chel chel = new Chel(this);
    WaitPersion waitPersion = new WaitPersion(this);
    ExecutorService executorService = Executors.newCachedThreadPool();
    public Restaurant(){
        executorService.execute(waitPersion);
        executorService.execute(chel);
    }

    public static void main(String[]  args) {
        new Restaurant();
    }
}

class Meal{
    private final int ordernum;
    public Meal(int ordernum){
        this.ordernum = ordernum;
    }

    @Override
    public String toString() {
        return "meal"+ordernum;
    }
}

class WaitPersion implements Runnable{
    public Restaurant restaurant;
    public WaitPersion(Restaurant restaurant){
        this.restaurant =restaurant;
    }
    @Override
    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted()){
                synchronized (this){
                    while(restaurant.meal == null){
                        wait();
                    }
                }
                System.out.println("waitpersion got "+restaurant.meal);
                synchronized (restaurant.chel){
                    restaurant.meal = null;
                    restaurant.chel.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("waitPersion interupted");
        }
    }
}

class Chel implements Runnable{
    private  Restaurant restaurant;
    private int count;
    public Chel(Restaurant restaurant){
        this.restaurant = restaurant;
    }
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                synchronized (this){
                    while(restaurant.meal!=null){
                        wait();
                    }
                }
                if(++count==10){
                    System.out.println("out of food,close");
                    restaurant.executorService.shutdownNow();
                }
                System.out.println("order up");
                synchronized (restaurant.waitPersion){
                    restaurant.meal = new Meal(count);
                    restaurant.waitPersion.notifyAll();
                }
                TimeUnit.MILLISECONDS.sleep(300);
            }
        } catch (InterruptedException e) {
            System.out.println("chef interupted");
        }
    }
}