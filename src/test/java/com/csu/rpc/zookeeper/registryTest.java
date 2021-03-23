package com.csu.rpc.zookeeper;

import com.csu.rpc.ZookeeperUtil;
import com.csu.rpc.registry.impl.ZookeeperRegistry;
import com.csu.rpc.utils.SingletonFactory;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Author Chen Yu
 * @Date 2021/3/22 19:14
 */
public class registryTest {

    private final ZookeeperRegistry registry = SingletonFactory.getInstance(ZookeeperRegistry.class);
    private final ZookeeperUtil zookeeperUtil = SingletonFactory.getInstance(ZookeeperUtil.class);

    @Test
    public void Test() {
        registry.registerService("testService", new InetSocketAddress("127.0.0.1" ,8080));
        registry.unRegisterService("testService", new InetSocketAddress("127.0.0.1" ,8080));
        registry.registerService("testService", new InetSocketAddress("127.0.0.1" ,8180));
    }

    @Test
    public void getAllService() {
        List<String> children = zookeeperUtil.getChildren(ZookeeperRegistry.ZOOKEEPER_ADDRESS, "/rpc/HelloService");

        for (String s : children) {
            System.out.println(s);
        }
    }

    @Test
    public void delete() {
        zookeeperUtil.deleteNode(ZookeeperRegistry.ZOOKEEPER_ADDRESS, "/testService");
    }
}
