package com.csu.rpc;

import com.csu.rpc.annotation.RpcScan;
import com.csu.rpc.controller.HelloController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * @Author Chen Yu
 * @Date 2021/3/25 20:07
 */
@RpcScan(basePackage = {"com.csu.rpc"})
public class ClientMain {

    private static final Integer numThread = 2;

    public static void main(String[] args) throws NoSuchFieldException, InterruptedException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientMain.class);

        HelloController controller = context.getBean(HelloController.class);
        controller.test();


    }
}
