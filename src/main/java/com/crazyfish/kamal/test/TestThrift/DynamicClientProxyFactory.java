package com.crazyfish.kamal.test.TestThrift;

import java.util.List;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-07-21 下午5:20
 */
public class DynamicClientProxyFactory {
    public static Object createIface(String clazzIfaceName,List<String> servers){
        try{
            int idx = clazzIfaceName.lastIndexOf('$');
            String clazzClientName = clazzIfaceName.substring(0,idx) + "$Client";
            Class clientClazz = Class.forName(clazzClientName);
            DynamicClientProxy proxy = new DynamicClientProxy();
            return proxy.createProxy(clientClazz);
        }catch (Exception e){

        }
        return null;
    }
}
