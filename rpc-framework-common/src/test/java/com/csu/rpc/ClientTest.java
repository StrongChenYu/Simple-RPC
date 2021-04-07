package com.csu.rpc;

import com.csu.rpc.client.NettyClient;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import com.csu.rpc.server.NettyServer;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class ClientTest {

    @Test
    public void serverStartTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        Thread server = new Thread(() -> {
            NettyServer nettyServer = new NettyServer(8000);
            nettyServer.start(countDownLatch);
        });

        Thread client = new Thread(() -> {
            try {
                sendMessageTest();
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        server.start();
        client.start();

        countDownLatch.await();

        Thread.sleep(1000);
    }

    @Test
    public void sendMessageTest() throws InterruptedException {
        NettyClient client = new NettyClient();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName("HelloService")
                .methodName("sayHello")
                .args(null)
                .argTypes(null).build();


        RpcResponse rpcResponse = client.sendMessage(rpcRequest);
        System.out.println(rpcResponse);
    }

}
