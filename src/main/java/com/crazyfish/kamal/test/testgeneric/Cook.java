package com.crazyfish.kamal.test.testgeneric;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-01-28 下午1:23
 */
public class Cook<T extends Woman> {
    public void cook(T woman){
        System.out.println("cook " + woman.getName());
    }
}
