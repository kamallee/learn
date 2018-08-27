package com.crazyfish.kamal.test.testaop;

import net.sf.cglib.proxy.Enhancer;

public class MonkeyProxyFactory
{
    public static Monkey getAuthInstance()
    {
        Enhancer en = new Enhancer();
        // 设置要代理的目标类
        en.setSuperclass(Monkey.class);
        // 设置要代理的拦截器
        en.setCallback(new AroundAdvice());
        // 生成代理类的实例
        return (Monkey)en.create();
    }
}