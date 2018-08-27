package com.crazyfish.kamal.test.testgeneric;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-02-24 下午7:54
 */
public class Test {
    public static <T> List<T> asList(T...t){
        List<T> list = new ArrayList<>();
        Collections.addAll(list,t);
        return list;
    }
    public static void main(String args[]){
        List<String>  list1 = Test.asList("xx","xyy");
        List<String> list2 = Test.asList();
        List list3 = Test.<Number>asList(3,2,3.1);
        list2.add("2");

        read(list1);
    }

    public static <E> void read(List<? extends E> list){
        for(E obj : list){
            System.out.println(obj);
        }
    }
}
