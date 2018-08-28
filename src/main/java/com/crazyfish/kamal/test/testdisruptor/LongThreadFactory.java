package com.crazyfish.kamal.test.testdisruptor;

import java.util.concurrent.ThreadFactory;

/**
 * @Created with IntelliJ IDEA.
 * @Description:
 * @Author: lpp
 * @Date: 2018-08-28 16:20:26
 */
public class LongThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "test thread");
    }
}
