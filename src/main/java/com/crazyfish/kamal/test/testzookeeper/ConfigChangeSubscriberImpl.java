package com.crazyfish.kamal.test.testzookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by kamal on 15/8/12.
 */
public class ConfigChangeSubscriberImpl implements ConfigChangeSubscriber {
    private ZkClient client;
    private String rootNode;
    private CountDownLatch lat;
    public ConfigChangeSubscriberImpl(ZkClient client,String rootNode,CountDownLatch lat){
        this.client = client;
        this.rootNode = rootNode;
        this.lat = lat;
    }
    public String getInitValue(String key) {
        String path = ZkUtils.getZkPath(this.rootNode,key);
        return (String)client.readData(path);
    }

    public void subscribe(String key) {
        String path = ZkUtils.getZkPath(rootNode,key);
        if( !client.exists(path)){
            System.out.println("天，数据不存在查个毛线");
        }
        client.subscribeDataChanges(path,new DataChangeListener());
    }

    public List<String> listKeys() {//获取所有配置文件，及配置文件对应的节点
        return client.getChildren(rootNode);
    }

    private class DataChangeListener implements IZkDataListener{

        public void handleDataChange(String s, Object o) throws Exception {
            System.out.println("哈哈哈哈，数据变了，我该做些什么呢" + s + o.toString());
            lat.countDown();
        }

        public void handleDataDeleted(String s) throws Exception {
            System.out.println("靠，数据被删了。。");
            lat.countDown();
        }
    }
}
