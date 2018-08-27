package com.crazyfish.kamal.test.TestThrift;

import com.crazyfish.kamal.controller.IHelloWorldService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

/**
 * Created by kamal on 15/8/7.
 */
public class SimpleServer {
    public static int SERVER_PORT = 8712;
    public void startServer(){
        try{
            System.out.println("TSimple Server start...");
            TProcessor p = new IHelloWorldService.Processor<IHelloWorldService.Iface>(new HelloWorldServiceImple());
            TServerSocket soc = new TServerSocket(SERVER_PORT);
            TServer.Args args = new TServer.Args(soc);
            args.processor(p);
            args.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TSimpleServer(args);
            server.serve();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String args[]){
        SimpleServer server = new SimpleServer();
        server.startServer();
    }
}
