package com.csu.rpc.server.process;


import com.csu.rpc.bean.RpcServiceInfo;

/**
 * @Author Chen Yu
 * @Date 2021/3/22 20:37
 */
public interface ServerProvider {

    void publishServer(Object service, RpcServiceInfo serviceInfo);

    Object obtainService(RpcServiceInfo serviceInfo);

}
