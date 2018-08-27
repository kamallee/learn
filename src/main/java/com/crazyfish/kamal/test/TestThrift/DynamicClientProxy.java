package com.crazyfish.kamal.test.TestThrift;

import com.google.common.collect.Lists;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-07-21 下午4:42
 */
public class DynamicClientProxy<T> implements InvocationHandler {
    private  Class<T> tc = null;
    public Object createProxy(Class<T> tc){
        this.tc = tc;
        return Proxy.newProxyInstance(tc.getClassLoader(),tc.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<ServerNode> list = Lists.newArrayList();
        list.add(new ServerNode("127.0.0.1",8712));
        list.add(new ServerNode("127.0.1.1",8777));

        int timeout = 4000;
        for( ServerNode serverNode : list ){
            String ip = serverNode.getIp();
            int port = serverNode.getPort();
            System.out.println(ip + ":" + port);
            TSocket tSocket = null;
            try{
                tSocket = new TSocket(ip,port);
                tSocket.setTimeout(timeout);
                TProtocol tProtocol = new TBinaryProtocol(tSocket);
                Class[] argsClass = new Class[]{
                        TProtocol.class
                };
                Constructor<T> constructor = tc.getConstructor(argsClass);
                T client = constructor.newInstance(tProtocol);
                tSocket.open();
                return method.invoke(client,args);
            }catch(Exception e){

            }
        }
        return null;
    }

    class ServerNode{
        private String ip;
        private int port;

        public ServerNode(String ip,int port){
            this.ip = ip;
            this.port = port;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
