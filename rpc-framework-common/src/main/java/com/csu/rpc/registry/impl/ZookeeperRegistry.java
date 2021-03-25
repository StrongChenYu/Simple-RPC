package com.csu.rpc.registry.impl;

import com.csu.rpc.utils.ZookeeperUtil;
import com.csu.rpc.registry.ServiceRegistry;
import com.csu.rpc.utils.SingletonFactory;

import java.net.InetSocketAddress;


/**
 * zookeeper注册中心API
 *
 * @Author Chen Yu
 * @Date 2021/3/17 19:58
 */
public class ZookeeperRegistry implements ServiceRegistry {

    public static final String ZOOKEEPER_ADDRESS = "127.0.0.1:2181";
    private static final String SERVICE_PREFIX = "/rpc/";

    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        ZookeeperUtil zkUtil = SingletonFactory.getInstance(ZookeeperUtil.class);

        /**
         * 格式
         * name/ip1
         * name/ip2
         * name/ip3
         *
         * 这里如果不存在就创建一个新的节点
         * 因为是用IP分割的，所以需要判断一下列表里面是否已经存在了
         * 如果存在就在原有的内容后面加一个节点
         */
        String register = SERVICE_PREFIX + rpcServiceName + inetSocketAddress.toString();
        zkUtil.createPersistentNode(ZOOKEEPER_ADDRESS, register, null);
    }

    @Override
    public void unRegisterService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        ZookeeperUtil zkUtil = SingletonFactory.getInstance(ZookeeperUtil.class);

        /**
         * 删除节点，如果不存在就不用删除
         * 如果存在就删除掉
         */
        String register = SERVICE_PREFIX + rpcServiceName + inetSocketAddress.toString();
        zkUtil.deleteNode(ZOOKEEPER_ADDRESS, register);
    }
}
