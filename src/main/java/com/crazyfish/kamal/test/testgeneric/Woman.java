package com.crazyfish.kamal.test.testgeneric;

/**
 * @author lipengpeng
 * @desc 女人
 * @date 2016-01-28 下午1:15
 */
public class Woman implements Human {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Woman(String name){
        this.name = name;
    }
    @Override
    public void run() {
        System.out.println("woman run...");
    }
}
