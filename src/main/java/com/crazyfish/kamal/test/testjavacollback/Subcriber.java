package com.crazyfish.kamal.test.testjavacollback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by kamal on 15/8/20.
 */
public class Subcriber{

    public void subcribe(final IDataListener listener){//向Publisher订阅，等到数据变更通知我
        //同步订阅
//        Publisher publisher = new Publisher(new IDataListener() {//IDataListener实现，用于被回调
//            public void dataChangeListener() {
//                System.out.println("sync:我被调用了");
//            }
//        });
//        publisher.publish();
        //异步订阅
        final CountDownLatch lat = new CountDownLatch(1);
        new Thread(new Runnable() {
            public void run() {
                Publisher publisher = new Publisher(listener);
                publisher.publish(lat);
            }
        }).start();
        System.out.println("async:我干其他事情去了，等你发布了通知我哦");
        try{
            boolean rl = lat.await(30L, TimeUnit.SECONDS);
            if( rl ){
                System.out.println("async:我收到通知啦，谢谢通知" );
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        Subcriber subcriber = new Subcriber();
        subcriber.subcribe(listener());
    }

    public static IDataListener listener(){
        return new IDataListener() {//IDataListener实现，用于被回调
            public void dataChangeListener(String name,String ...paramss) {
                System.out.println("async:我被调用了" + name + paramss[0] + paramss[1]);

            }
        };
    }
}
