package com.csu.rpc.server;

import com.csu.rpc.client.NettyClient;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class ClientTest {

    @Test
    public void serverStartTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        Thread server = new Thread(() -> {
            NettyServer nettyServer = new NettyServer(8080);
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

        Thread.sleep(10000);
    }

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
