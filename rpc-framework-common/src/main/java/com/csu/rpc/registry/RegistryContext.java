package com.csu.rpc.registry;

import java.net.InetSocketAddress;

public class RegistryContext implements ServiceRegistry{

    private ServiceRegistry serviceRegistry;

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        serviceRegistry.registerService(rpcServiceName, inetSocketAddress);
    }

    @Override
    public void unRegisterService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        serviceRegistry.unRegisterService(rpcServiceName, inetSocketAddress);
    }
}
