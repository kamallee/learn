package com.crazyfish.kamal.test.TestThrift;

import com.crazyfish.kamal.controller.IHelloWorldService;
import org.apache.thrift.TException;

/**
 * Created by kamal on 15/8/7.
 */
public class HelloWorldServiceImple implements IHelloWorldService.Iface {
    @Override
    public String sayHello(String username) throws TException {
        return "hello," + username + ",welcome to thrift world!";
    }
}
