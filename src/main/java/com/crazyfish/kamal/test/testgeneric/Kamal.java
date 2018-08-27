package com.crazyfish.kamal.test.testgeneric;

/**
 * @author lipengpeng
 * @desc 具体的某一个人
 * @date 2016-01-28 下午1:16
 */
public class Kamal extends Man {
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Kamal(String name){
        this.name = name;
    }

    public void run(){
        System.out.println("kamal run ...");
    }
}
