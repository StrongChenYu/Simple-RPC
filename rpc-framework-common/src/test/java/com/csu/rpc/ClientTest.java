package com.csu.rpc;

import com.csu.rpc.client.NettyClient;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import com.csu.rpc.server.NettyServer;
import com.csu.rpc.utils.SingletonFactory;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class ClientTest {

    @Test
    public void serverStartTest() throws InterruptedException {

    }

    @Test
    public void sendMessageTest() throws InterruptedException {
        NettyClient client = SingletonFactory.getInstance(NettyClient.class);
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName("HelloService")
                .methodName("sayHello")
                .args(null)
                .argTypes(null).build();


        RpcResponse rpcResponse = client.sendMessage(rpcRequest);
        System.out.println(rpcResponse);
    }

}
