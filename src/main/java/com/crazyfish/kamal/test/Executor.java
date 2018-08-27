package com.crazyfish.kamal.test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Executor implements Watcher, Runnable, DataMonitor.DataMonitorListener {
    String znode;
    DataMonitor dm;
    ZooKeeper zk;
    String filename;
    String exec;
    Process child;

    public Executor(String hostPort, String znode, String filename,
                    String exec) throws KeeperException, IOException {
        this.filename = filename;
        this.exec = exec;
        zk = new ZooKeeper(hostPort, 3000, this);
        dm = new DataMonitor(zk, znode, null, this);
    }

    /**
     * @param args
     */
    public static void main1(String[] args) {
        String hostPort = "127.0.0.1:2181";
        String znode = "/one/hello";
        String filename = "/Users/kamal/Download/test.txt";
        //String exec[] = new String[args.length - 3];
        //System.arraycopy(args, 3, exec, 0, exec.length);
        String exec = new String("java -version");
        try {
            new Executor(hostPort, znode, filename, exec).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{/**一般不要在这里抛出异常，从设计耦合角度仔细考虑一下，
     这里的Exception污染了上层调用代码，因为调用层需要显式的利用try-catch捕捉，或者向更上层次进一步抛出。根据设计隔离原则**/
        List names = new ArrayList();//这里应该使用泛型，泛型能够保证类型的准确性
        names.add("John");
        names.add("Pierre");
        names.add(45); // Autoboxing wraps 45 into an Integer, which is stored
        try {
            for (int i = 0; i < names.size(); i++) {
                String name = (String) names.get(i);
                System.out.println(name);
            }
        }catch(ClassCastException e){//不要使用覆盖式异常处理块，不要用Exception处理所有异常
            System.out.println("class cast ex");//这里应该正确处理，而不是简单输出到控制台
            e.printStackTrace();
            throw new RuntimeException("error");//抛出后后面代码不再执行，包括finally代码块
            //return;
            //System.exit(2);//finally不执行
        }catch(Exception e){//捕获所有异常
            e.printStackTrace();
        }finally {
            System.out.println("if do finally");//在return之前执行
        }
        System.out.println("do it ?");
        //由于发送异常没有正确的处理，导致后续抛出更多异常
        System.out.println("you must not do this" + (String)names.get(2));
    }

    /**
     * ************************************************************************
     * We do process any events ourselves, we just need to forward them on.
     *
     * @see org.apache.zookeeper.Watcher# process(org.apache.zookeeper.proto.WatcherEvent)
     */
    public void process(WatchedEvent event) {
        dm.process(event);
    }

    public void run() {
        try {
            synchronized (this) {
                while (!dm.dead) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
        }
    }

    public void closing(int rc) {
        synchronized (this) {
            notifyAll();
        }
    }

    static class StreamWriter extends Thread {
        OutputStream os;
        InputStream is;
        StreamWriter(InputStream is, OutputStream os) {
            this.is = is;
            this.os = os;
            start();
        }
        public void run() {
            byte b[] = new byte[80];
            int rc;
            try {
                while ((rc = is.read(b)) > 0) {
                    os.write(b, 0, rc);
                }
            } catch (IOException e) {
            }

        }
    }

    public void exists(byte[] data) {
        if (data == null) {
            if (child != null) {
                System.out.println("Killing process");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                }
            }
            child = null;
        } else {
            if (child != null) {
                System.out.println("Stopping child");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileOutputStream fos = new FileOutputStream(filename);
                fos.write(data);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("Starting child");
                child = Runtime.getRuntime().exec(exec);
                new StreamWriter(child.getInputStream(), System.out);
                new StreamWriter(child.getErrorStream(), System.err);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}