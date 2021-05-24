package com.csu.rpc.server.process;

import com.csu.rpc.bean.RpcServiceInfo;
import com.csu.rpc.server.process.processImpl.ServerProviderImpl;
import com.csu.rpc.utils.SingletonFactory;

/**
 * @Author Chen Yu
 * @Date 2021/3/22 20:37
 */
public interface ServerProvider {

    ServerProvider INSTANCE = SingletonFactory.getInstance(ServerProviderImpl.class);

    void publishServer(Object service, RpcServiceInfo serviceInfo);

    Object obtainService(RpcServiceInfo serviceInfo);

}
