package com.yunbin.circuitbreaker;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

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
    
    
    @Test
    public void addTest6() {
        SlidingWindow slidingWindow = new SlidingWindow(3);
        CyclicBarrier barrier = new CyclicBarrier(100);
        CountDownLatch countDownLatch = new CountDownLatch(100);
        
        for (int i = 0; i < 100; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < 100; i++) {
                        slidingWindow.add(1L);
                    }
                    countDownLatch.countDown();
                }
            }.start();
            
        }
        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int count = slidingWindow.count(2L);
        assertThat(count).isEqualTo(100 * 100);
    }
    
    
}
