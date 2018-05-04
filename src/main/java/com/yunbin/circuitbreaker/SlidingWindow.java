package com.yunbin.circuitbreaker;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by cloud.huang on 18/5/3.
 */
public class SlidingWindow {
    private int size;
    private AtomicInteger[] counts;
    private  long lastTime = 0;
    
    public SlidingWindow(int size) {
        this.size = size;
        counts = new AtomicInteger[size];
        for (int i = 0; i < size; i++) {
            counts[i] = new AtomicInteger();
        }
    }
    
    public void add(long time) {
        clear(time);
        int index = (int) (time % size);
        if (time > lastTime) {
            lastTime = time;
            counts[index].set(1);
        } else {
            counts[index].getAndIncrement();
        }
    }
    
    public int count(long time) {
        clear(time);
        int result = 0;
        for (AtomicInteger count : counts) {
            result += count.get();
        }
        return result;
    }
    
    private void clear(long time) {
        if (time < lastTime + size) {
            return;
        }
        for (int i = 0; i < size; i++) {
            counts[i].set(0);
        }
    }
    
    
}
