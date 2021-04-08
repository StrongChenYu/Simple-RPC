package com.csu.rpc.server.process.processImpl;

import com.csu.rpc.bean.ServiceInfo;
import com.csu.rpc.constant.RpcConstants;
import com.csu.rpc.enums.RegistryTypeEnum;
import com.csu.rpc.registry.ServiceRegistry;
import com.csu.rpc.registry.impl.ZookeeperRegistry;
import com.csu.rpc.server.process.ServerProvider;
import com.csu.rpc.utils.SingletonFactory;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Chen Yu
 * @Date 2021/3/22 20:45
 */
public class ServerProviderImpl implements ServerProvider {
    
    private final RegistryTypeEnum registerType =  RegistryTypeEnum.ZOOKEEPER;

    /**
     * service -> serviceInfo的映射
     */
    private final Map<String, ServiceInfo> servicesMap = new ConcurrentHashMap<>();


    private ServiceRegistry getRegistry() {
        return SingletonFactory.getInstance(registerType.getClazz());
    }

    @Override
    public void publishServer(Object serviceImpl, Class<?> interFace, Integer port) {
        String serviceName = interFace.getSimpleName();

        ServiceInfo info = ServiceInfo.builder()
                .name(serviceName)
                .serverImpl(serviceImpl)
                .interFace(interFace)
                .port(port).build();

        System.out.println("注册服务：" + serviceName);
        publishServer(info);
    }

    @Override
    public ServiceInfo obtainService(String serviceName) {
        return servicesMap.get(serviceName);
    }

    public void publishServer(ServiceInfo info) {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(RpcConstants.IP, info.getPort());
        ServiceRegistry serviceRegistry = getRegistry();

        /**
         * 这里主要是让注册中心找到
         */
        serviceRegistry.registerService(info.getName(), inetSocketAddress);
        servicesMap.put(info.getName(), info);
    }

}
