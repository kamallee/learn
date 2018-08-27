package com.crazyfish.kamal.test.TestSomething;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-02-24 下午1:51
 */
public class Buffer {
    private Object lock = this;
    private ReentrantLock lock1 = new ReentrantLock();
    private Condition condition = lock1.newCondition();
    public void write(){
//        synchronized (lock){
//            long startTime = System.currentTimeMillis();
//            System.out.println("start write");
//            for(;;){
//                if( System.currentTimeMillis() - startTime > Integer.MAX_VALUE)
//                    break;
//            }
//            System.out.println("write end");
//        }
        lock1.lock();
        try{
            long startTime = System.currentTimeMillis();
            System.out.println("start write");
            for(;;){
                long start = System.currentTimeMillis();
                for (;;) {
                    if (System.currentTimeMillis() - start > 2000) {
                        System.out.println("太累了，休息一下，主动放弃锁");
                        condition.await();
                        System.out.println("重新获得锁继续执行");
                        break;
                    }
                }
                if( System.currentTimeMillis() - startTime > Integer.MAX_VALUE)
                    break;
            }
            System.out.println("write end");
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            lock1.unlock();
        }
    }
    public void read()throws InterruptedException{
//        synchronized (lock){
//            System.out.println("read");
//        }
        //lock1.lockInterruptibly();
        if(lock1.tryLock(2, TimeUnit.SECONDS)){//1改成>2就可以了
            System.out.println("尝试获得锁成功，获得锁");
        }else{
            System.out.println("尝试失败，没有获得锁");
        }
        try{
            System.out.println("read");
            //condition.signal();//发送信号让write继续执行
        }finally {
            lock1.unlock();//如果没有获得锁，放弃锁会抛异常
        }
    }
    public static void main(String args[]){
        Buffer buffer = new Buffer();
        final Writer writer = new Writer(buffer);
        final Reader reader = new Reader(buffer);
        writer.start();
        reader.start();
        //等待一段时间发出中断请求，Syn不能中断，Lock可以
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                for (;;) {
                    if (System.currentTimeMillis() - start > 5000) {
                        //System.out.println("不等了，尝试中断");
                        //reader.interrupt();
                        break;
                    }
                }
            }
        }).start();
    }
}
class Writer extends Thread{
    private Buffer buff;
    public Writer(Buffer buf){
        this.buff = buf;
    }
    @Override
    public void run(){
        buff.write();//使用并发代码
    }
}
class Reader extends Thread{
    private Buffer buff;
    public Reader(Buffer buff){
        this.buff = buff;
    }
    @Override
    public void run(){
        try {
            buff.read();//收到异常
        } catch (InterruptedException e) {
            System.out.println("不等待锁了，退出");
        }
        System.out.println("read end");
    }
}
