package com.csu.rpc.zookeeper;

import com.csu.rpc.ZookeeperUtil;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

/**
 * @Author Chen Yu
 * @Date 2021/3/18 19:16
 */
public class apiTest{

    ZookeeperUtil zookeeperRegistry = new ZookeeperUtil();

    @Test
    public void connectTest() throws Exception {
        //CuratorFramework zkClient = zookeeperRegistry.getZkClient();
        //CuratorFrameworkState state = zkClient.getState();

        //zkClient.delete().forPath("/path1");
    }

    @Test
    public void getDataTest() throws Exception {
        //CuratorFramework zkClient = zookeeperRegistry.getZkClient();
        //byte[] bytes = zkClient.getData().forPath("/path");
        //System.out.println(bytes);
//        String s = new String(bytes, StandardCharsets.UTF_8);
//        System.out.println(s);
    }

//    @Before
//    public void createNode() {
//        CuratorFramework zkClient = zookeeperRegistry.getZkClient();
//        try {
//            if (zkClient.checkExists().forPath("/path") == null) {
//                zkClient.create().withMode(CreateMode.PERSISTENT).forPath("/path");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void multiThreadDelete() throws InterruptedException {
        int threadNum = 100;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        //zookeeperRegistry.deleteNode("/path");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        countDownLatch.countDown();
        Thread.sleep(5000);
    }

    @Test
    public void checkNodeExist() throws Exception {
        //CuratorFramework zkClient = zookeeperRegistry.getZkClient();
//        Stat stat = zkClient.checkExists().forPath("/path1");
//        System.out.println(stat);
    }

    @Test
    public void Test() {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1",8080);
        System.out.println(address.toString());
    }
}
