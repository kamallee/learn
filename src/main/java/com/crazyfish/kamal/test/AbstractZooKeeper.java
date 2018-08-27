package com.crazyfish.kamal.test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * @author kamal
 * @version
 */
public class AbstractZooKeeper implements Watcher {
    //缓存时间
    private static final int SESSION_TIME   = 2000;
    protected ZooKeeper zooKeeper;
    protected CountDownLatch countDownLatch=new CountDownLatch(1);

    public void connect(String hosts) throws IOException, InterruptedException{
        zooKeeper = new ZooKeeper(hosts,SESSION_TIME,this);
        countDownLatch.await();
        long sessionId = zooKeeper.getSessionId();
        byte[] passwd = zooKeeper.getSessionPasswd();
        zooKeeper = new ZooKeeper(hosts, SESSION_TIME, this, 123l, "abc1".getBytes());
        zooKeeper = new ZooKeeper(hosts,SESSION_TIME,this,sessionId,passwd);
    }

    public void process(WatchedEvent event) {//进行相应处理会提醒注册到这个节点的客户端
        System.out.println("event:" + event);
        if( event.getState() == KeeperState.SyncConnected ){
            System.out.println("回调，连接上服务器");
            countDownLatch.countDown();//告知连接上服务器
        }
        try {
            if (event.getType() == Event.EventType.NodeDeleted) {
                System.out.println("delete");
                zooKeeper.exists(event.getPath(), true);
            } else if (event.getType() == Event.EventType.NodeCreated) {
                System.out.println("create");
                zooKeeper.exists(event.getPath(), true);
            } else if (event.getType() == Event.EventType.NodeDataChanged) {
                System.out.println("data changed ");
                zooKeeper.exists(event.getPath(), true);
            } else {
                System.out.println("nothing");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void close() throws InterruptedException{
        zooKeeper.close();
    }
}
