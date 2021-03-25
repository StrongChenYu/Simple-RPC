package com.csu.rpc;

import com.csu.rpc.server.NettyServer;
import com.csu.rpc.service.HelloService;
import com.csu.rpc.service.HelloServiceImpl;

/**
 * @Author Chen Yu
 * @Date 2021/3/25 20:07
 */
public class Main {

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer(8000);
        nettyServer.scanAddService(new HelloServiceImpl(), HelloService.class);
        nettyServer.start();
    }
}
