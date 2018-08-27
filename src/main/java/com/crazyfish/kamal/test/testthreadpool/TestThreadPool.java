package com.crazyfish.kamal.test.testthreadpool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author lipengpeng
 * @desc 并发包线程池使用
 * @date 2016-01-27 上午11:06
 */
public class TestThreadPool {
    public static void main(String args[]) throws InterruptedException {
        //基本方法，不建议直接使用，建议优先使用Execurors中的静态方法
        ThreadPoolExecutor service = new ThreadPoolExecutor(4,4,0, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>(10)){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行:" + ((Task)r).getName());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行完成:" + ((Task)r).getName());
            }

            @Override
            protected void terminated() {
                System.out.println("执行退出");
            }
        };

        for(int i = 0;i < 10;i ++){
            //service.execute(new Task("task" + i));
            //System.out.println("线程池中线程数目：" + service.getPoolSize() + "，队列中等待执行的任务数目：" +
             //       service.getQueue().size() + "，已执行完的任务数目：" + service.getCompletedTaskCount());
        }
        //固定大小线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for(int i = 0;i < 10;i ++){
            //executorService.execute(new Task());
        }
        //有返回值的异步任务
        List<Future<String>> list = new LinkedList<Future<String>>();
        for(int i = 0;i < 10;i ++){
            Future<String> rlt = executorService.submit(new MyCallable("task" + i));
            list.add(rlt);
            //这种使用列表保存返回结果稍微有点复杂，而且有不足的地方。如果第一个任务耗费非常长的时间来执行，然后其他的任务都早于它结束，
            // 那么当前线程就无法在第一个任务结束之前获得执行结果，别着急，Java 为你提供了解决方案——CompletionService。
        }
        for(Future<String> f : list){
            try {
                System.out.println("rlt:" + f.get(3,TimeUnit.SECONDS));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        //使用CompletionService
        CompletionService<String> pool = new ExecutorCompletionService<String>(executorService);
        for(int i = 0;i < 10;i ++){
            pool.submit(new MyCallable("task" + i));
        }
        try {
            for(int i = 0;i < 10;i ++){
                    String rlt = pool.take().get();
                    System.out.println("comp:" + rlt );
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(executorService.isShutdown() + "" + executorService.isTerminated());
        //平缓的关闭线程池。线程池停止接受新的任务，同时等待已经提交的任务执行完毕，包括那些进入队列还没有开始的任务。shutdown()方法执行过程中，线程池处于SHUTDOWN状态
        //executorService.shutdown();//如果你不这么做，JVM 并不会去关闭这些线程
        //立即关闭线程池。线程池停止接受新的任务，同时线程池取消所有执行的任务和已经进入队列但是还没有执行的任务
        executorService.shutdownNow();
        try {
            Thread.sleep(2000);//等待线程池中的任务执行完成
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(executorService.isShutdown() + "" + executorService.isTerminated());
    }

    static class Task implements Runnable{
        private String name;
        public Task(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + name + " run ...");
        }
    }

    static class MyCallable implements Callable<String>{
        private String name;
        public MyCallable(String name){
            this.name = name;
        }

        @Override
        public String call() throws Exception {
            return "task " + name + " return result";
        }
    }
}
