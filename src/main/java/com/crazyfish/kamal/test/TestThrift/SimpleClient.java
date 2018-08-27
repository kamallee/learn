package com.crazyfish.kamal.test.TestThrift;

import com.crazyfish.kamal.controller.IHelloWorldService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by kamal on 15/8/7.
 */
public class SimpleClient {
    public static final String SERVER_IP = "127.0.0.1";
    public static final int SERVER_PORT = 8712;
    public static final int TIMEOUT = 30000;
    public void startClient(String name){
        TTransport transport = null;
        try{
            transport = new TSocket(SERVER_IP,SERVER_PORT,TIMEOUT);
            TProtocol pro = new TBinaryProtocol(transport);
            IHelloWorldService.Client client = new IHelloWorldService.Client(pro);
            transport.open();
            String rlt = client.sayHello(name);
            System.out.println("client:" + rlt);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String args[]){
        SimpleClient client = new SimpleClient();
        client.startClient("lpp");
    }
}
