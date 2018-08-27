package com.crazyfish.kamal.test.testzookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by kamal on 15/8/11.
 */
public class ZkConfigPublisher {
    public static String CONF_DIR = "src/main/java/com/crazyfish/kamal/test/test/conf";//配置文件放的目录
    public static String ZK_ADDRESS = "";
    public static int ZK_TIMEOUT = 100000;
    public static String ZK_CONFIG_ROOTNODE = "";
    public static String ZK_CONFIG_ENCODING = "";

    public static void loadProperties(){
        Properties pros = new Properties();
        try {
            InputStream is = ZkConfigPublisher.class.getResourceAsStream("/conf/zkconfig.properties");
            pros.load(is);
        }catch(Exception e){
            e.printStackTrace();
        }
        ZK_ADDRESS = pros.getProperty("ZK_ADDRESS");
        ZK_TIMEOUT = Integer.parseInt(pros.getProperty("ZK_TIMEOUT"));
        ZK_CONFIG_ENCODING = pros.getProperty("ZK_CONFIG_ENCODING");
        ZK_CONFIG_ROOTNODE = pros.getProperty("ZK_CONFIG_ROOTNODE");
    }

    public static void main1(String args[]){
        loadProperties();
        ZkClient client = new ZkClient(ZK_ADDRESS,ZK_TIMEOUT);
        client.setZkSerializer(new ZkUtils.StringSerializer(ZK_CONFIG_ENCODING));
        File confDir = new File(CONF_DIR);
        System.out.println("dir:" + confDir.getAbsolutePath());
        if( !confDir.exists() || !confDir.isDirectory()){
            System.out.println("目录" + confDir + "不存在");
            return;
        }
        publishConfig(client,ZK_CONFIG_ROOTNODE,confDir);
    }

    public static void main(String args[]) {
        loadProperties();
        ZkClient client = new ZkClient(ZK_ADDRESS,ZK_TIMEOUT);
        client.setZkSerializer(new ZkUtils.StringSerializer(ZK_CONFIG_ENCODING));
        CountDownLatch lat = new CountDownLatch(1);
        ConfigChangeSubscriber sub = new ConfigChangeSubscriberImpl(client,ZK_CONFIG_ROOTNODE,lat);
        sub.subscribe("conf1.xml");
        client.writeData(ZK_CONFIG_ROOTNODE + "/conf1.xml","1231xxxx");
        try {
            boolean not = lat.await(30L, TimeUnit.SECONDS);
            if (!not) {
                System.out.println("meitongzhi");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private static void publishConfig(ZkClient client,String rootNode,File dir){
        File[] confs = dir.listFiles();
        for( File file : confs ){
            if( !file.isFile() ){
                continue;
            }
            String name = file.getName();
            String path = ZkUtils.getZkPath(rootNode,name);
            ZkUtils.mkPath(client,path);
            String content = null;
            try{
                content = FileUtils.readFileToString(file);
            }catch(IOException e){
                e.printStackTrace();
            }
            if( !client.exists(path)){
                try{
                    client.createPersistent(path);
                    client.writeData(path,content);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                System.out.println(path + "exists:add :" + content);
                client.writeData(path, content);
                System.out.println("data:" + client.readData(path));
            }
        }
    }
}
