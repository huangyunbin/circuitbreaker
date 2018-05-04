package com.yunbin.circuitbreaker;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by cloud.huang on 18/5/3.
 */
public class SlidingWindow {
    private int size;
    private AtomicIntegerArray counts;
    private volatile long lastTime = 0;
    
    public SlidingWindow(int size) {
        this.size = size;
        counts = new AtomicIntegerArray(size);
    }
    
    public void add() {
        long currentSecond = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
        add(currentSecond);
    }
    
    
    public  void add(long time) {
        clear(time);
        int index = (int) (time % size);
        if (time > lastTime) {
            lastTime = time;
            counts.set(index, 1);
        } else {
            counts.getAndIncrement(index);
        }
    }
    
    public int count() {
        long currentSecond = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
        return count(currentSecond);
    }
    
    public int count(long time) {
        clear(time);
        int result = 0;
        for (int i = 0; i < size; i++) {
            result += counts.get(i);
        }
        return result;
    }
    
    private void clear(long time) {
        if (time < lastTime + size) {
            return;
        }
        for (int i = 0; i < size; i++) {
            counts.set(i, 0);
        }
    }
    
    
}
