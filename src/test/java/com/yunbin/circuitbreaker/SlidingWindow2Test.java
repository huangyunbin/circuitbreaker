package com.yunbin.circuitbreaker;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by cloud.huang on 18/5/6.
 */
public class SlidingWindow2Test {
    
    
    @Test
    public void addTest1() {
        SlidingWindow2 slidingWindow = new SlidingWindow2(1000);
        slidingWindow.success();
        slidingWindow.success();
        slidingWindow.fail();
        slidingWindow.success();
        slidingWindow.fail();
        assertThat(slidingWindow.getSucessNum()).isEqualTo(3);
        assertThat(slidingWindow.getFailNum()).isEqualTo(2);
    }
    
    @Test
    public void addTest2() {
        SlidingWindow2 slidingWindow = new SlidingWindow2(3);
        slidingWindow.success();
        slidingWindow.fail();
        slidingWindow.success();
        slidingWindow.fail();
        slidingWindow.success();
        slidingWindow.fail();
        assertThat(slidingWindow.getSucessNum()).isEqualTo(1);
        assertThat(slidingWindow.getFailNum()).isEqualTo(2);
    }
    
    
    @Test
    public void addTest4() {
        SlidingWindow2 slidingWindow = new SlidingWindow2(3);
        slidingWindow.fail();
        slidingWindow.fail();
        slidingWindow.fail();
        slidingWindow.success();
        slidingWindow.success();
        slidingWindow.success();
        assertThat(slidingWindow.getSucessNum()).isEqualTo(3);
        assertThat(slidingWindow.getFailNum()).isEqualTo(0);
    }
    
    
    @Test
    public void addTest5() {
        SlidingWindow2 slidingWindow = new SlidingWindow2(3);
        slidingWindow.success();
        slidingWindow.success();
        slidingWindow.success();
        slidingWindow.fail();
        slidingWindow.fail();
        slidingWindow.fail();
        assertThat(slidingWindow.getSucessNum()).isEqualTo(0);
        assertThat(slidingWindow.getFailNum()).isEqualTo(3);
    }
    
    
}
