package com.ch.study.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by chenhao on 2017/3/8.
 * 使用 {@link java.util.concurrent.Semaphore}
 */
public class Pool<T> {
    private int size;
    private List<T> items = new ArrayList<T>();
    private volatile boolean[] checkOut;
    private Semaphore semaphore;
    public Pool(Class<T> tClass,int size){
        checkOut = new boolean[size];
        semaphore = new Semaphore(size,true);
        for(int i=0;i<size;i++){
            try {
                items.add(tClass.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    private synchronized T getItem(){
        for(int i=0;i<size;i++){
            if(!checkOut[i]){
                checkOut[i]=true;
                return items.get(i);
            }
        }
        return null;
    }
    private synchronized boolean releaseItem(T item){
        int index = items.indexOf(item);
        if(index == -1){
            return false;
        }
        if(checkOut[index]){
            checkOut[index]=false;
            return true;
        }
        return false;
    }
    public T checkOut() throws InterruptedException {
        semaphore.acquire();
        return getItem();
    }
    public void checkIn(T item){
        if(releaseItem(item)){
            semaphore.release();
        }
    }
}
