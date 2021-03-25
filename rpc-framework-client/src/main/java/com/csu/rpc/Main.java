package com.csu.rpc;

import com.csu.rpc.client.NettyClient;
import com.csu.rpc.dto.request.RpcRequest;

/**
 * @Author Chen Yu
 * @Date 2021/3/25 20:07
 */
public class Main {

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient("127.0.0.1", 8000);

        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName("HelloService")
                .interfaceName("HelloService")
                .methodName("sayHello")
                .args(null)
                .argTypes(null).build();

        nettyClient.sendMessage(rpcRequest);
    }
}
