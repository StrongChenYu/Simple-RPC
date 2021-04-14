package com.csu.rpc.server.process.processImpl;


import com.csu.rpc.bean.RpcServiceInfo;
import com.csu.rpc.constant.RpcConstants;
import com.csu.rpc.registry.ServiceRegistry;
import com.csu.rpc.server.NettyServer;
import com.csu.rpc.server.process.ServerProvider;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Chen Yu
 * @Date 2021/3/22 20:45
 */
public class ServerProviderImpl implements ServerProvider {

    ServiceRegistry registry = ServiceRegistry.INSTANCE;
    Map<RpcServiceInfo, Object> serviceMap = new ConcurrentHashMap<>();

    @Override
    public void publishServer(Object service, RpcServiceInfo serviceInfo) {
        //给default的属性赋值
        String serviceName = serviceInfo.getServiceName();
        if (serviceName == null || serviceName.equals("")) {
            Class<?> serviceInterface = service.getClass().getInterfaces()[0];
            serviceInfo.setServiceName(serviceInterface.getSimpleName());
        }

        String registerName = serviceInfo.toRegisterRpcServiceName();
        //注册到注册中心中
        registry.registerService(registerName, new InetSocketAddress(RpcConstants.IP, RpcConstants.DEFAULT_PORT));
        //注册到服务端，以便以后能找到
        serviceMap.put(serviceInfo, service);
    }

    @Override
    public Object obtainService(RpcServiceInfo serviceInfo) {
        return serviceMap.get(serviceInfo);
    }
}
