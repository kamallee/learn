package com.crazyfish.kamal.test.testdisruptor;

/**
 * @Created with IntelliJ IDEA.
 * @Description:
 * @Author: lpp
 * @Date: 2018-08-28 15:11:11
 */
public class LongEvent {
    private long value;
    public void set(long value){
        this.value = value;
    }
    public long get(){
        return value;
    }
}
