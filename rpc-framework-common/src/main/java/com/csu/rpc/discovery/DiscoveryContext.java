package com.csu.rpc.discovery;

import com.csu.rpc.bean.RpcServiceInfo;

import java.net.InetSocketAddress;

public class DiscoveryContext implements ServerDiscovery{

    ServerDiscovery serverDiscovery;

    public void setServerDiscovery(ServerDiscovery serverDiscovery) {
        this.serverDiscovery = serverDiscovery;
    }

    @Override
    public InetSocketAddress lookupServer(RpcServiceInfo serviceInfo) {

        return serverDiscovery.lookupServer(serviceInfo);
    }

}
