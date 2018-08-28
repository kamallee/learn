package com.crazyfish.kamal.test.testdisruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @Created with IntelliJ IDEA.
 * @Description:
 * @Author: lpp
 * @Date: 2018-08-28 15:20:32
 */
/*
Disruptor 的事件发布过程是一个两阶段提交的过程：
　　第一步：先从 RingBuffer 获取下一个可以写入的事件的序号；
　　第二步：获取对应的事件对象，将数据写入事件对象；
　　第三部：将事件提交到 RingBuffer;
事件只有在提交之后才会通知 EventProcessor 进行处理
 */
public class LongEventProducer {
    private final RingBuffer<LongEvent> ringBuffer;
    public LongEventProducer(RingBuffer<LongEvent> ringBuffer){
        this.ringBuffer = ringBuffer;
    }
    public void onData(ByteBuffer bb){
        long sequence = ringBuffer.next();
        try{
            LongEvent event = ringBuffer.get(sequence);
            event.set(bb.getLong(0));
        }finally {
            ringBuffer.publish(sequence);
        }
    }
}
