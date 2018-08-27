package com.crazyfish.kamal.test.testzookeeper;


import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by kamal on 15/8/11.
 */
public class ZkUtils {
    public static String getZkPath(String rootNode,String key){
        if( !StringUtils.isEmpty(rootNode)){
            if( key.startsWith("/")){
                key = key.substring(1);
            }
            if( rootNode.endsWith("/")){
                return rootNode + key;
            }
            return rootNode + "/" + key;
        }
        return key;
    }

    public static void mkPath(ZkClient client,String path){
        String[] subs = path.split("/");
        //System.out.println("size:" + subs.length + ":" + subs[0] + ":" + subs[1] + ":" + subs[2]);
        String tmp = "";
        for( int i = 1;i < subs.length;i ++){
            tmp = tmp + "/" + subs[i];
            if( !client.exists(tmp)) {
                client.createPersistent(tmp);
            }
        }
    }

    public static class StringSerializer implements ZkSerializer{
        private String encoding;

        public StringSerializer(String encoding){
            this.encoding = encoding;
        }
        public byte[] serialize(Object o) throws ZkMarshallingError {
            if( o == null){
                return null;
            }
            if( (o instanceof  String )){
                try{
                    return ((String)o).getBytes(this.encoding);
                }catch(UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        public Object deserialize(byte[] bytes) throws ZkMarshallingError {
            try{
                return new String(bytes,this.encoding);
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
