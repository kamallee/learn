package com.crazyfish.kamal.test;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;

/**
 * Created by kamal on 15/7/24.
 */
public class DataMonitor implements Watcher , AsyncCallback.StatCallback{
    ZooKeeper zk;
    String znode;
    Watcher chainedWatcher;
    boolean dead;
    DataMonitorListener listener;
    byte prevData[];

    public DataMonitor(ZooKeeper zk,String znode,Watcher chainedWatcher,DataMonitorListener listener){
        this.zk = zk;
        this.znode = znode;
        this.chainedWatcher = chainedWatcher;
        this.listener = listener;
        zk.exists(znode,true,this,null);
    }
    public interface DataMonitorListener{
        void exists(byte data[]);
        void closing(int rc);
    }

    public void processResult(int i, String s, Object o, Stat stat) {
        boolean exists;
        switch (i){
            case KeeperException.Code.Ok:
                exists = true;
                break;
            case KeeperException.Code.NoNode:
                exists = false;
                break;
            case KeeperException.Code.SessionExpired:
            case KeeperException.Code.NoAuth:
                dead = true;
                listener.closing(i);
                return;
            default:
                zk.exists(znode,true,this,null);
                return;
        }
        byte b[] = null;
        if( exists ){
            try{
                b = zk.getData(znode,false,null);
            }catch( KeeperException ke ){
                ke.printStackTrace();
            } catch ( InterruptedException e){
                return;
            }
        }
        if ( (b == null && b != prevData) || (b != null && !Arrays.equals(prevData,b))){
            listener.exists(b);
            prevData = b;
        }
    }

    public void process(WatchedEvent watchedEvent) {
        String path = watchedEvent.getPath();
        if( watchedEvent.getType() == Event.EventType.None){
            switch(watchedEvent.getState()){
                case SyncConnected:
                    break;
                case Expired:
                    dead = true;
                    listener.closing(KeeperException.Code.SessionExpired);
                    break;
            }
        } else {
            if( path != null && path.equals(znode)){
                zk.exists(znode,true,this,null);
            }
        }
        if(chainedWatcher != null){
            chainedWatcher.process(watchedEvent);
        }
    }
}
