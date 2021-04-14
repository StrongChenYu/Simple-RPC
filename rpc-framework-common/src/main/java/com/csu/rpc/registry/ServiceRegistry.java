package com.csu.rpc.registry;

import com.csu.rpc.registry.impl.ZookeeperRegistry;

import java.net.InetSocketAddress;

/**
 * 用来向注册中心添加服务
 *
 * @Author Chen Yu
 * @Date 2021/3/17 19:08
 */
public interface ServiceRegistry {

    ServiceRegistry INSTANCE = new ZookeeperRegistry();

    /**
     * 注册服务
     * @param rpcServiceName
     * @param inetSocketAddress
     */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);

    /**
     * 取消注册服务
     * @param rpcServiceName
     * @param inetSocketAddress
     */
    void unRegisterService(String rpcServiceName, InetSocketAddress inetSocketAddress);
}
