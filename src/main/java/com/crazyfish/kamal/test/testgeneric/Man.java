package com.crazyfish.kamal.test.testgeneric;

/**
 * @author lipengpeng
 * @desc 男人
 * @date 2016-01-28 下午1:16
 */
public class Man implements Human {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Man(){}
    
    public Man(String name){
        this.name = name;
    }
    @Override
    public void run() {
        System.out.println("man run...");
    }
}
