package com.crazyfish.kamal.test.testaop;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class AroundAdvice implements MethodInterceptor {
    public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws java.lang.Throwable {
        System.out.println("执行目标方法之前...");
        // 执行目标方法，并保存目标方法执行后的返回值
        Object rvt = proxy.invokeSuper(target, new String[] { "被改变的参数,我" });
        System.out.println("执行目标方法之后...");
        return rvt + " 新增的内容";
    }
}