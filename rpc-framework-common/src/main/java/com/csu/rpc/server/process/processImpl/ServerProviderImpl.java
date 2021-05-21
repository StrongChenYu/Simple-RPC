package com.csu.rpc.server.process.processImpl;


import com.csu.rpc.bean.RpcServiceInfo;
import com.csu.rpc.registry.ServiceRegistry;
import com.csu.rpc.server.process.ServerProvider;
import com.csu.rpc.config.RpcConfig;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Chen Yu
 * @Date 2021/3/22 20:45
 */
public class ServerProviderImpl implements ServerProvider {

    ServiceRegistry registry = ServiceRegistry.INSTANCE;
    Map<RpcServiceInfo, Object> serviceMap = new ConcurrentHashMap<>();

    private final RpcConfig rpcConfig = RpcConfig.RPC_CONFIG;

    @Override
    public void publishServer(Object service, RpcServiceInfo serviceInfo) {
        //给default的属性赋值
        String serviceName = serviceInfo.getServiceName();
        if (serviceName == null || serviceName.equals("")) {
            Class<?>[] interfaces = service.getClass().getInterfaces();
            if (interfaces.length == 0) throw new RuntimeException("service interface can not be null!");

            Class<?> serviceInterface = interfaces[0];
            serviceInfo.setServiceName(serviceInterface.getSimpleName());
        }

        String registerName = serviceInfo.toRegisterRpcServiceName();


        //注册到注册中心中
        registry.registerService(registerName, new InetSocketAddress(rpcConfig.getServerConfig().getIp(),
                rpcConfig.getServerConfig().getPort()));
        //注册到服务端，以便以后能找到
        serviceMap.put(serviceInfo, service);
    }

    @Override
    public Object obtainService(RpcServiceInfo serviceInfo) {
        return serviceMap.get(serviceInfo);
    }
}
