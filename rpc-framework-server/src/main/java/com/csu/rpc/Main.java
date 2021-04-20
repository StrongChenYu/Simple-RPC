package com.csu.rpc;

import com.csu.rpc.annotation.RpcScan;
import com.csu.rpc.server.NettyServer;
import com.csu.rpc.service.HelloService;
import com.csu.rpc.service.HelloServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author Chen Yu
 * @Date 2021/3/25 20:07
 */
@RpcScan(basePackage = {"com.csu.rpc"})
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        NettyServer server = context.getBean(NettyServer.class);
        server.start();
    }
}
