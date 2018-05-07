package com.yunbin.circuitbreaker;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by cloud.huang on 18/5/6.
 */
public class SlidingWindow2 {
    private final int size;
    private final AtomicBitSet bitSet;
    private volatile AtomicInteger index = new AtomicInteger();
    private volatile AtomicInteger capacity = new AtomicInteger();
    
    public SlidingWindow2(int size) {
        this.size = size;
        bitSet = new AtomicBitSet(size);
    }
    
    
    public void success() {
        processCapacity();
        setNext(true);
    }
    
    public void fail() {
        processCapacity();
        setNext(false);
    }
    
    private void setNext(boolean flag) {
        int target = index.getAndIncrement() % size;
        bitSet.set(target, flag);
    }
    
    private void processCapacity() {
        if (capacity.get() < size) {
            capacity.getAndIncrement();
        }
    }
    
    
    public long getSucessNum() {
        return bitSet.cardinality();
    }
    
    public long getFailNum() {
        if (capacity.get() >= size) {
            return size - bitSet.cardinality();
        } else {
            return capacity.get() - bitSet.cardinality();
        }
        
    }
}
