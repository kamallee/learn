package com.crazyfish.kamal;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class T  implements Cloneable{
    public static int k = 0;
    public static T t1 = new T("t1");
    public static T t2 = new T("t2");
    public static int i = print("i");
    public static int n = 99;

    public int j = print("j");
    {
        print("构造块");
    }

    static {
        print("静态块");
    }

    public T(String str) {
        System.out.println((++k) + ":" + str + "    i=" + i + "  n=" + n);
        ++n; ++ i;
    }

    public static int print(String str){
        System.out.println((++k) +":" + str + "   i=" + i + "   n=" + n);
        ++n;
        return ++ i;
    }
    enum Day{SUNDAY,MONDAY};
    public static void main(String[] args){
        //T t = new T("init");
        ConcurrentHashMap hash = new ConcurrentHashMap();
        HashMap h = new HashMap();
        Hashtable has = new Hashtable();
        System.out.println("123");
        //(float) box * 100f;
        int y = 53276000;
        float x = (float)53276000 * 100f;
        System.out.println("xx:" + x);
//        long start = System.currentTimeMillis();
//        for(int i = 0 ;i < 100000000;i ++) {
//            try {
//                hash.put("123",123);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("s:" + (end -start) + "ms");
//
//        has.clear();
//        long start1 = System.currentTimeMillis();
//        try {
//            for(int i = 0;i < 100000000;i ++) {
//                has.put("123",123);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        long end1 = System.currentTimeMillis();
//        System.out.println("s1:" + (end1 -start1) + "ms");
        //System.out.println()
    }
    public static void test(Day type){
        switch(type){
            case SUNDAY:
                System.out.println("xx");

        }
    }
}
