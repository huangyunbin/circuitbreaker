package com.yunbin.circuitbreaker;

/**
 * Created by cloud.huang on 18/5/3.
 */
public class SlidingWindow {
    private int count = 0;
    
    public SlidingWindow(int l) {
    }
    
    public void add(long l) {
        count++;
    }
    
    public int count(long l) {
        return count;
    }
    
    public int getCount() {
        return count;
    }
    
    
}
