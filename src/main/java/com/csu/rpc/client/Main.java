package com.csu.rpc.client;

import com.csu.rpc.serializer.request.RpcRequest;
import com.csu.rpc.serializer.response.RpcResponse;

public class Main {
    public static void main(String[] args) {
        NettyClient client = new NettyClient("127.0.0.1", 8080);
        RpcRequest request = new RpcRequest();
        request.setInterfaceName("interfece1");
        request.setMethodName("method2");

        RpcResponse response = client.sendMessage(request);
        System.out.println(response);
    }
}
