package com.csu.rpc.utils;

import com.csu.rpc.registry.LoadBalance;
import com.csu.rpc.registry.ServerDiscovery;
import com.csu.rpc.registry.ServiceRegistry;
import com.csu.rpc.registry.impl.RandomLoadBalance;
import com.csu.rpc.registry.impl.ZkServerDiscovery;
import com.csu.rpc.registry.impl.ZookeeperRegistry;
import com.csu.rpc.server.process.RpcRequestHandler;
import com.csu.rpc.server.process.ServerProvider;

/**
 * @Author Chen Yu
 * @Date 2021/4/13 19:21
 */
public class MockBeanContext {

    public static final RpcRequestHandler DEFAULT_REQUEST_HANDLER = SingletonFactory.getInstance(RpcRequestHandler.class);
    public static final ServerProvider DEFAULT_SERVER_PROVIDER = SingletonFactory.getInstance(ServerProvider.class);
    public static final LoadBalance DEFAULT_LOAD_BALANCE = SingletonFactory.getInstance(RandomLoadBalance.class);
    public static final ServerDiscovery DEFAULT_SERVER_DISCOVERY = SingletonFactory.getInstance(ZkServerDiscovery.class);
    public static final ServiceRegistry DEFAULT_SERVER_REGISTRY = SingletonFactory.getInstance(ZookeeperRegistry.class);


}
