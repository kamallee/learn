package com.crazyfish.kamal.test.TestSomething;

import java.util.concurrent.CyclicBarrier;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-05-05 下午7:25
 */
public class TestCylicBarrier {
    static class Runner extends Thread{
        private CyclicBarrier cylicBarrier;
        public Runner(CyclicBarrier cylicBarrier){
            this.cylicBarrier = cylicBarrier;
        }
        @Override
        public void run(){
            try{
                Thread.sleep(3000);
                System.out.println("线程" + Thread.currentThread().getName() + "执行完毕!");
                cylicBarrier.await();//线程挂起，等待其他线程执行完毕
            }catch(Exception e){
                e.printStackTrace();
            }
            System.out.println("所有线程执行完毕..");
        }
    }

    public static void main(String args[]){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);
        for(int i = 0 ;i < 4;i ++){
            new Runner(cyclicBarrier).start();
        }
        try{
            Thread.sleep(3000);
        }catch(Exception e){}
        for(int i = 0 ;i < 4;i ++){
            new Runner(cyclicBarrier).start();//可以重复使用CyclicBarrier对象，而CountDownLatch不行
        }
    }
}
