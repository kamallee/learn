package com.crazyfish.kamal.test.TestSomething;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by kamal on 15/8/24.
 */
public class DynamicProxy implements InvocationHandler {
    //代理对象
    public Object obj;
    public DynamicProxy(Object obj){
        this.obj = obj;
    }
    //代理方法
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("查找权限，result-->i can't " + method.getName() + ",but by proxy");
        Object o = method.invoke(obj,args);
        return o;
    }
    public static void main(String args[]){
        try {
            IUserLogin lo = new UserLoginImpl();
            DynamicProxy inv = new DynamicProxy(lo);
            IUserLogin l = (IUserLogin) Proxy.newProxyInstance(lo.getClass().getClassLoader(), lo.getClass().getInterfaces(), inv);
            l.login("lpp");
            l.register();
        }catch (Exception  e){
            e.printStackTrace();
            System.exit(2);
        }
    }
}
