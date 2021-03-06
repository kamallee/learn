package com.crazyfish.kamal.test.testkafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kamal on 15/8/17.
 */
public class ConsumerClient {
    private final ConsumerConnector consumerConnector;
    private final String topic;
    private ExecutorService service;
    public ConsumerClient(String zk,String groupid,String topic){
        this.topic = topic;
        Properties pro = new Properties();
        pro.put("zookeeper.connect",zk);
        pro.put("group.id",groupid);
        pro.put("zookeeper.session.timeout.ms", "4000");
        pro.put("zookeeper.sync.time.ms", "200");
        pro.put("auto.commit.interval.ms", "1000");
        ConsumerConfig config = new ConsumerConfig(pro);
        consumerConnector = Consumer.createJavaConsumerConnector(config);
    }
    public void run(int threadsNum){
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(threadsNum));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

        //创建线程
        service = Executors.newFixedThreadPool(threadsNum);

        //创建消费消息线程
        int threadNumber = 0;
        for (final KafkaStream stream : streams) {
            service.submit(new ConsumerMsgTask(stream, threadNumber));
            threadNumber++;
        }
    }
    public void shutdown(){
        if(consumerConnector != null)
        consumerConnector.shutdown();
        if (service != null)
            service.shutdown();
    }
    public static void main(String args[]) {
        ConsumerClient client = new ConsumerClient("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183","one","first");
        client.run(3);
        try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        client.shutdown();
    }

    public class ConsumerMsgTask implements Runnable {
        private KafkaStream stream;
        private int threadNumber;

        public ConsumerMsgTask(KafkaStream stream, int threadNumber) {
            this.threadNumber = threadNumber;
            this.stream = stream;
        }

        public void run() {
            ConsumerIterator<byte[], byte[]> it = stream.iterator();
            while (it.hasNext())
                System.out.println("Thread " + threadNumber + ": "
                        + new String(it.next().message()));
            System.out.println("Shutting down Thread: " + threadNumber);
        }
    }
}

