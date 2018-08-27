package com.crazyfish.kamal.test.testspringtask;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kamal on 16/1/13.
 */
@Component
public class TestSpringTask implements InitializingBean{//初始化bean时执行下面方法
    @Resource(name="myExecutor")
    private AsyncTaskExecutor executor;


    public void afterPropertiesSet() throws Exception {
        final AtomicInteger count = new AtomicInteger();
        for(int i = 0;i < 100;i ++) {
            executor.execute(new Runnable() {
                public void run() {
                    System.out.println("xx:" + count.incrementAndGet());
                }
            });
        }
    }
}
