package com.csu.rpc;

import com.csu.rpc.registry.ServerDiscovery;
import com.csu.rpc.registry.impl.ZkServerDiscovery;
import com.csu.rpc.utils.SingletonFactory;
import com.csu.rpc.utils.ZookeeperUtil;
import org.junit.Test;

import java.util.List;

/**
 * @Author Chen Yu
 * @Date 2021/4/7 20:28
 */
public class ZkDiscoveryTest {

    private ServerDiscovery serverDiscovery = SingletonFactory.getInstance(ZkServerDiscovery.class);
    private ZookeeperUtil zkUtils = SingletonFactory.getInstance(ZookeeperUtil.class);

    @Test
    public void findAllServer() {
        List<String> helloService = zkUtils.getChildren("127.0.0.1:2181", "/rpc/HelloService");

        for (String service : helloService) {
            System.out.println(service);
        }
    }

}
