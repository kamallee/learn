package com.crazyfish.kamal.test.testdesignpattern.testflyweight;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * @author lipengpeng
 * @desc 享元模式
 * @date 2016-02-03 下午5:17
 */
public class FlyWeight {
    public static void main(String args[]){
        String a = "abc";
        String b = "abc";
        String c = new String("abc");
        String d = c.intern();
        String e = new String("ccc");
        e.intern();
        String f = "ccc";
        System.out.println(e == f);


        System.out.println(a == d);
        System.out.println(a == b );
        System.out.println(a == c);
        SimpleDateFormat format = new SimpleDateFormat();

        Integer arr[] = {1,22,3,23,6};
        List list = Arrays.asList(arr);
        System.out.println("size:" + list.size());
        list.add(3);
    }
}
