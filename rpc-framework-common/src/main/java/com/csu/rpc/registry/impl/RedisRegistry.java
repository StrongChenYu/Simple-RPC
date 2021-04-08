package com.csu.rpc.registry.impl;

import com.csu.rpc.registry.ServiceRegistry;

import java.net.InetSocketAddress;

/**
 * @Author Chen Yu
 * @Date 2021/3/17 19:58
 */
public class RedisRegistry implements ServiceRegistry {
    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {

    }

    @Override
    public void unRegisterService(String rpcServiceName, InetSocketAddress inetSocketAddress) {

    }
}
