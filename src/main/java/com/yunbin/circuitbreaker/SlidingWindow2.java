package com.yunbin.circuitbreaker;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by cloud.huang on 18/5/6.
 */
public class SlidingWindow2 {
    private int size;
    private volatile AtomicIntegerArray counts;
    private volatile AtomicLong lastTime = new AtomicLong();
    
    public SlidingWindow2(int size) {
        this.size = size;
        counts = new AtomicIntegerArray(size);
    }
    
    
}
