package com.crazyfish.kamal.test.testgeneric;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-01-28 下午1:23
 */
public class Work<T extends Man> {
    public void working(T man){
        System.out.println("working " + man.getName());
    }
}
