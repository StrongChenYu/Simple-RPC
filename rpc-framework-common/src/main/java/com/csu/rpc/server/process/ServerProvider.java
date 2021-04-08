package com.csu.rpc.server.process;


import com.csu.rpc.bean.ServiceInfo;
import com.csu.rpc.server.process.processImpl.ServerProviderImpl;
import com.csu.rpc.utils.SingletonFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Author Chen Yu
 * @Date 2021/3/22 20:37
 */
public interface ServerProvider {

    ServerProvider INSTANCE = new ServerProviderImpl();

    void publishServer(Object serviceImpl, Class<?> interFace, Integer port);

    ServiceInfo obtainService(String serviceName);

}
