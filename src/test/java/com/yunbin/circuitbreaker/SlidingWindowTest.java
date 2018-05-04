package com.yunbin.circuitbreaker;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

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
        int threadNum = 10;
        final int num = 5;
        final int circle = 1;
        final SlidingWindow slidingWindow = new SlidingWindow(10);
        final CyclicBarrier barrier = new CyclicBarrier(threadNum);
        final CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        
        for (int i = 0; i < threadNum; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        barrier.await();
                        
                        for (int i = 0; i < circle; i++) {
                            for (int j = 0; j < num; j++) {
                                slidingWindow.add();
                            }
                            
                            TimeUnit.MILLISECONDS.sleep(1000);
                        }
                        
                        countDownLatch.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            
        }
        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int count = slidingWindow.count();
        assertThat(count).isEqualTo(circle * threadNum * num);
    }
    
    
}
