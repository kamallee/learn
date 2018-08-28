package com.crazyfish.kamal.test.testdisruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Created with IntelliJ IDEA.
 * @Description:
 * @Author: lpp
 * @Date: 2018-08-28 15:28:53
 */
public class LongEventMain {
    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        LongEventFactory factory = new LongEventFactory();
        LongThreadFactory threadFactory = new LongThreadFactory();
        int bufferSize = 1024;
//        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, bufferSize, service);
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, bufferSize, threadFactory, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            producer.onData(bb);
            Thread.sleep(1000);
        }
    }
}
