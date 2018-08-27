package com.crazyfish.kamal.test.testjavacollback;

import java.util.concurrent.CountDownLatch;

/**
 * Created by kamal on 15/8/20.
 */
public class Publisher {
    IDataListener listener;
    public Publisher(IDataListener listener){
        this.listener = listener;//获取监听接口，谁实现他就会调用哪个实现函数
    }
    //同步调用
    public void publish(){//发布信息，会通知sucriber，怎么通知呢？通过回调
        try{
            Thread.sleep(3000);//模拟等待一段时间才发布消息，或者处理时间
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("sync:我发布信息啦，你已经告诉我要通知你，所以我来通知你了");
        String name = "lpP";
        //listener.dataChangeListener(name,"xxx","yyy");
    }
    //异步调用
    public void publish(CountDownLatch lat){//发布信息，会通知sucriber，怎么通知呢？通过回调
        try{
            Thread.sleep(3000);//模拟等待一段时间才发布消息，或者处理时间
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("async:我发布信息啦，你已经告诉我要通知你，所以我来通知你了");
        ManModel m = new ManModel();
        listener.dataChangeListener(m.getStatus(), "xxx", "yyy");
        System.out.println("修改状态1" + "setStatus->上班");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m.setStatus(ManModel.y);
        listener.dataChangeListener(m.getStatus(),"change","chagne2");
        System.out.println("修改状态2");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m.setStatus(ManModel.z);
        listener.dataChangeListener(m.getStatus(),"mmm2","hhh2");
        lat.countDown();//已经通知到位
    }
}
