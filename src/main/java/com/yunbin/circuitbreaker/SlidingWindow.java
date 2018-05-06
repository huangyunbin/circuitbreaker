package com.yunbin.circuitbreaker;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by cloud.huang on 18/5/3.
 */
public class SlidingWindow {
    private int size;
    private volatile AtomicIntegerArray counts;
    private volatile AtomicLong lastTime = new AtomicLong();
    
    public SlidingWindow(int size) {
        this.size = size;
        counts = new AtomicIntegerArray(size);
    }
    
    public void add() {
        long currentSecond = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
        add(currentSecond);
    }
    
    
    public  void add(long time) {
//        clear(time);
        int index = (int) (time % size);
        long current = lastTime.longValue();
        
        if (time > current) {
            int oldValue = counts.get(index);
            if (lastTime.compareAndSet(current, time)) {
                counts.getAndAdd(index, 1 - oldValue);
            } else {
                counts.getAndIncrement(index);
            }
//            System.out.println("======" + index);
            
        } else {
            counts.getAndIncrement(index);
//            System.out.println("------" + index);
        }
    }
    
    public int count() {
        long currentSecond = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
        return count(currentSecond);
    }
    
    public int count(long time) {
//        clear(time);
        int result = 0;
        for (int i = 0; i < size; i++) {
            result += counts.get(i);
        }
        return result;
    }
    
    private void clear(long time) {
//        if (time < lastTime + size) {
//            return;
//        }
//        for (int i = 0; i < size; i++) {
//            counts.set(i, 0);
//        }
    }
    
    
}
