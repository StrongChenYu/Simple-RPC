package com.csu.rpc.discovery;

import com.csu.rpc.bean.RpcServiceInfo;

import java.net.InetSocketAddress;

public class RedisServerDiscovery implements ServerDiscovery{
    @Override
    public InetSocketAddress lookupServer(RpcServiceInfo serviceInfo) {
        return new InetSocketAddress("127.0.0.1", 8080);
    }
}
