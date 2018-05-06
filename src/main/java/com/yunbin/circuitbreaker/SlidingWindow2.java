package com.yunbin.circuitbreaker;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by cloud.huang on 18/5/6.
 */
public class SlidingWindow2 {
    private final int size;
    //成功为0，失败为1
    private final BitSet bitSet;
    private final AtomicInteger index = new AtomicInteger();
    private volatile AtomicInteger capacity = new AtomicInteger();
    
    public SlidingWindow2(int size) {
        this.size = size;
        bitSet = new BitSet(size);
    }
    
    
    public void success() {
        if (capacity.get() < size) {
            capacity.getAndIncrement();
        }
        int target = index.getAndIncrement() % size;
        bitSet.set(target, false);
    }
    
    public void fail() {
        if (capacity.get() < size) {
            capacity.getAndIncrement();
        }
        int target = index.getAndIncrement() % size;
        bitSet.set(target);
    }
    
    public long getSucessNum() {
        if (capacity.get() >= size) {
            return size - bitSet.cardinality();
        }
        int max = capacity.get();
        int result = 0;
        for (int i = 0; i < max; i++) {
            if (!bitSet.get(i)) {
                result++;
            }
        }
        return result;
    }
    
    public long getFailNum() {
        return bitSet.cardinality();
    }
}
