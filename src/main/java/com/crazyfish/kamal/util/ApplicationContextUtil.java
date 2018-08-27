package com.crazyfish.kamal.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by kamal on 15/7/31.
 */
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Value("#{mafkaProducerConfig['metadata.broker.list']}")
    private String brokerList;
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicationContextUtil function" + brokerList);
        this.applicationContext = applicationContext;
    }

    public static Object getBean(String beanPath){
        try {
            Object obj = applicationContext.getBean(beanPath);
            return obj;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
