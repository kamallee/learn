package com.crazyfish.kamal.test.TestSomething;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-05-06 下午2:35
 */
public class TestSemaphore {
    public static void main(String args[]){
        ExecutorService executor = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(5);
        for(int i = 0;i < 10;i ++){
            final int who = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try{
                        semaphore.acquire();
                        System.out.println("谁获得了许可-》" + who);
                        Thread.sleep((long) (Math.random() * 100));
                        semaphore.release();
                        System.out.println("release 剩余可用：" + semaphore.availablePermits());
                    }catch(Exception e){}
                }
            };
            executor.execute(runnable);
        }
        executor.shutdown();
    }
}
