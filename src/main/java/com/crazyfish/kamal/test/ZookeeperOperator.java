package com.crazyfish.kamal.test;

import java.util.List;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

/**
 * @author kamal
 * @version
 */
public class ZookeeperOperator extends AbstractZooKeeper{
    public void create(String path,byte[] data)throws KeeperException, InterruptedException{
        this.zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public void getChild(String path) throws KeeperException, InterruptedException{
        try{
            List<String> list = this.zooKeeper.getChildren(path, false);
            if( list.isEmpty() ){
                System.out.println(path + "中没有节点");
            }else{
                System.out.println(path + "中存在节点");
                for(String child:list){
                    System.out.println("节点为：" + child);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] getData(String path,Stat stat) throws KeeperException, InterruptedException {
        return  this.zooKeeper.getData(path, true,stat);
    }

    public static void main(String[] args) {
        try {
            Stat stat = new Stat();//节点中的信息都可以在这里获得
            Stat stat1 = new Stat();
            ZookeeperOperator zkoperator = new ZookeeperOperator();
            zkoperator.connect("10.4.246.91:2181");
            byte[] data = new byte[]{'a','b','c','d'};
            try {
                zkoperator.create("/one",data);
                zkoperator.create("/one/node2", data);//创建节点并写入数据
            }catch(KeeperException.NodeExistsException e){
                e.printStackTrace();
            }

            zkoperator.getChild("/one");
            System.out.println(new String(zkoperator.getData("/one/node2", stat), "utf-8"));
            String rlt = "kamal";
            zkoperator.zooKeeper.getData("/one/node2",false,new IDataCallBack(),rlt);
            System.out.println("stat1:" + stat.getVersion());
            stat = zkoperator.zooKeeper.setData("/one/node2","node2data change".getBytes(),stat.getVersion());//版本号不断递增
            System.out.println("stat2:" + stat.getVersion());
            try {
                zkoperator.getData("/one/node1", stat1);//获取stat1,包括版本号
                stat1 = zkoperator.zooKeeper.setData("/one/node1", "node1data".getBytes(), stat1.getVersion());//使用版本号
            }catch (Exception e){
                e.printStackTrace();
            }

            if( zkoperator.zooKeeper.exists("/one/node1",zkoperator) != null){//注册watcher
                System.out.println("存在/one/node1并且删除之");
                zkoperator.zooKeeper.delete("/one/node1", stat1.getVersion());//删除某一版本的节点，删除后wacher会通知
            }else{
                System.out.println("不存在/one/node1并且创建之");
                zkoperator.create("/one/node1","创建并写入".getBytes());
            }

            try {
                stat = zkoperator.zooKeeper.setData("/one/node2", "node2data change 2".getBytes(), 1);//错误的版本号，BadVersion异常
            }catch(KeeperException.BadVersionException e){
                e.printStackTrace();
            }
            System.out.println("stat3:" + stat.getVersion());
            zkoperator.close();
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class IDataCallBack implements AsyncCallback.DataCallback{
        public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
            System.out.println("callback:" + (String)o + ":i:" + i + ":s:" + s  + new String(bytes));
        }
    }
}
