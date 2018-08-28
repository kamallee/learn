package com.crazyfish.kamal.test.testdisruptor;


import com.lmax.disruptor.EventHandler;

/**
 * @Created with IntelliJ IDEA.
 * @Description:
 * @Author: lpp
 * @Date: 2018-08-28 15:16:35
 */
public class LongEventHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("Event:" + event.get());
    }
}
