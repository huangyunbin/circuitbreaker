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
    
    /**
     * 计数
     */
    public void add() {
        long currentSecond = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
        add(currentSecond);
    }
    
    
    public void add(long time) {
        clear(time);
        
        int index = (int) (time % size);
        long current = lastTime.longValue();
        
        if (time > current) {
            int oldValue = counts.get(index);
            if (lastTime.compareAndSet(current, time)) {
                counts.getAndAdd(index, 1 - oldValue);
            } else {
                counts.getAndIncrement(index);
            }
            
        } else {
            counts.getAndIncrement(index);
        }
    }
    
    /**
     * 清除上个周期的数据
     */
    private void clear(long time) {
        if (time < lastTime.get() + size) {
            return;
        }
        int index = (int) (time % size);
        for (int i = 0; i < size; i++) {
            if (i != index) {
                counts.set(i, 0);
            }
        }
    }
    
    /**
     * 统计
     */
    public int count() {
        long currentSecond = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime());
        return count(currentSecond);
    }
    
    public int count(long time) {
        if (time >= lastTime.get() + size) {
            return 0;
        }
        int result = 0;
        for (int i = 0; i < size; i++) {
            result += counts.get(i);
        }
        return result;
    }
    
    
}
