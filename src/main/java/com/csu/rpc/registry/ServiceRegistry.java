package com.csu.rpc.registry;

import java.net.InetSocketAddress;

/**
 * 用来向注册中心添加服务
 *
 * @Author Chen Yu
 * @Date 2021/3/17 19:08
 */
public interface ServiceRegistry {

    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);
}
