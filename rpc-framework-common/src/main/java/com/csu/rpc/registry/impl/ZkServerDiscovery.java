package com.csu.rpc.registry.impl;

import com.csu.rpc.registry.LoadBalance;
import com.csu.rpc.registry.ServerDiscovery;
import com.csu.rpc.utils.SingletonFactory;
import com.csu.rpc.utils.ZookeeperUtil;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;

/**
 * @Author Chen Yu
 * @Date 2021/4/7 19:52
 */
public class ZkServerDiscovery implements ServerDiscovery {

    private LoadBalance loadBalance = SingletonFactory.getInstance(RandomLoadBalance.class);
    private ZookeeperUtil zkUtils = SingletonFactory.getInstance(ZookeeperUtil.class);

    public static final String ZOOKEEPER_ADDRESS = "127.0.0.1:2181";
    private static final String SERVICE_PREFIX = "/rpc/";

    @Override
    public InetSocketAddress lookupServer(String serviceName) {
        /**
         * 获取拥有该服务的所有服务器
         */
        List<String> children = zkUtils.getChildren(ZOOKEEPER_ADDRESS, SERVICE_PREFIX + serviceName);

        if (children == null || children.size() == 0) {
            //这里可以抛一个runTimeException
            return null;
        }

        String serverPath = loadBalance.selectServer(children, serviceName);

        //log
        System.out.println(new Date() + "choose:" + serverPath);

        String[] splits = serverPath.split(":");
        return new InetSocketAddress(splits[0], Integer.parseInt(splits[1]));
    }
}
