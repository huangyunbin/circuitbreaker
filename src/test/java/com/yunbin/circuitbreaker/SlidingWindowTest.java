package com.yunbin.circuitbreaker;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by cloud.huang on 18/5/3.
 */
public class SlidingWindowTest {
    
    @Test
    public void addTest() {
        SlidingWindow slidingWindow = new SlidingWindow(3);
        slidingWindow.add(1L);
        int count = slidingWindow.count(3L);
        assertThat(count).isEqualTo(1);
    }
}
