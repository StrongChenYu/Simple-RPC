package com.csu.rpc.server;

import com.csu.rpc.client.NettyClient;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import org.junit.Test;

public class ClientTest {

    @Test
    public void sendMessageTest() throws InterruptedException {
        NettyClient client = new NettyClient("127.0.0.1", 8080);
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setInterfaceName("interface");
        rpcRequest.setMethodName("method");
        RpcResponse rpcResponse = client.sendMessage(rpcRequest);
        System.out.println(rpcResponse);
    }

}
