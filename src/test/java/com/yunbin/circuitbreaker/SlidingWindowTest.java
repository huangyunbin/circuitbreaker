package com.yunbin.circuitbreaker;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by cloud.huang on 18/5/3.
 */
public class SlidingWindowTest {
    
    @Test
    public void addTest1() {
        SlidingWindow slidingWindow = new SlidingWindow(3);
        slidingWindow.add(1L);
        int count = slidingWindow.count(3L);
        assertThat(count).isEqualTo(1);
    }
    
    
    @Test
    public void addTest2() {
        SlidingWindow slidingWindow = new SlidingWindow(3);
        slidingWindow.add(1L);
        slidingWindow.add(1L);
        int count = slidingWindow.count(3L);
        assertThat(count).isEqualTo(2);
    }
    
    @Test
    public void addTest3() {
        SlidingWindow slidingWindow = new SlidingWindow(3);
        slidingWindow.add(1L);
        slidingWindow.add(1L);
        int count = slidingWindow.count(4L);
        assertThat(count).isEqualTo(0);
    }
    
    
    @Test
    public void addTest4() {
        SlidingWindow slidingWindow = new SlidingWindow(3);
        slidingWindow.add(1L);
        slidingWindow.add(2L);
        slidingWindow.add(3L);
        int count = slidingWindow.count(3L);
        assertThat(count).isEqualTo(3);
    }
    
    
    @Test
    public void addTest5() {
        SlidingWindow slidingWindow = new SlidingWindow(3);
        slidingWindow.add(1L);
        slidingWindow.add(2L);
        slidingWindow.add(3L);
        slidingWindow.add(4L);
        int count = slidingWindow.count(4L);
        assertThat(count).isEqualTo(3);
    }
}
