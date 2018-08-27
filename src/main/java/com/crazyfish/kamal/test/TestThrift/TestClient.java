package com.crazyfish.kamal.test.TestThrift;

import com.crazyfish.kamal.controller.IHelloWorldService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-07-21 下午5:33
 */
public class TestClient {
    public static void main(String args[]) throws Exception{
        //DynamicClientProxy<IHelloWorldService.Client> clientDynamicClientProxy = new DynamicClientProxy<>();
        //IHelloWorldService.Iface service = (IHelloWorldService.Iface)clientDynamicClientProxy.createProxy(IHelloWorldService.Client.class);
        //System.out.print(service.sayHello("kamal"));
//        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/conf/applicationContext.xml");
//        IHelloWorldService.Iface service = (IHelloWorldService.Iface)applicationContext.getBean("helloService");
//        System.out.print(service.sayHello("kkk"));

        Class clazz = Class.forName("com.crazyfish.kamal.test.TestThrift.SimpleClient");

        Constructor<SimpleClient> ser = clazz.getConstructor();
        SimpleClient client = ser.newInstance();
        Method[] methods = clazz.getMethods();
        Method method = clazz.getMethod("startClient",String.class);
        method.invoke(client,"xxyy");
        //client.startClient("222");
    }
}
