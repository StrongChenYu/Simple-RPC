package com.csu.rpc.registry;

import com.csu.rpc.bean.RpcServiceInfo;
import com.csu.rpc.registry.impl.ZkServerDiscovery;

import java.net.InetSocketAddress;

/**
 * @Author Chen Yu
 * @Date 2021/4/7 19:49
 */
public interface ServerDiscovery {


    /**
     * 根据rpc的serviceName去zookeeper或者其他地方找到服务器地址
     * @return
     */
    InetSocketAddress lookupServer(RpcServiceInfo serviceInfo);

}
