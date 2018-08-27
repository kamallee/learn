package com.crazyfish.kamal.test.testguava;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-02-24 上午10:35
 */
public class TestEventBus {
    public static void main(String args[]){
        final EventBus eventBus = new EventBus();
        eventBus.register(new Object(){
            @Subscribe
            public void lister(Integer integer) {
                System.out.printf("%s from int%n", integer);
            }

            @Subscribe
            public void lister(Number integer) {
                System.out.printf("%s from Number%n", integer);
            }

            @Subscribe
            public void lister(Long integer) {
                System.out.printf("%s from long%n", integer);
            }
        });
        eventBus.post(1);
        eventBus.post(1L);
    }
}
