package com.crazyfish.kamal.test.testkafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import java.util.Properties;

/**
 * Created by kamal on 15/8/17.
 */
public class ProducerClient {
    public static void main(String args[]) {
        Properties pro = new Properties();
        pro.setProperty("metadata.broker.list","127.0.0.1:9092");
        pro.setProperty("serializer.class","kafka.serializer.StringEncoder");
        ProducerConfig config = new ProducerConfig(pro);
        Producer<String,String> producer = new Producer<String,String>(config);
        KeyedMessage<String,String> data = new KeyedMessage<String, String>("first","1222","woshi");
        producer.send(data);
        producer.close();
    }
}
