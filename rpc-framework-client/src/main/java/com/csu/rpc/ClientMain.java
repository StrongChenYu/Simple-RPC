package com.csu.rpc;

import com.csu.rpc.annotation.RpcScan;
import com.csu.rpc.config.CustomClientConfig;
import com.csu.rpc.controller.HelloController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author Chen Yu
 * @Date 2021/3/25 20:07
 */
@RpcScan(basePackage = {"com.csu.rpc"})
public class ClientMain {

    private static final Integer numThread = 1000;

    public static void main(String[] args) throws NoSuchFieldException {

        Thread[] threads = new Thread[numThread];

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientMain.class);

        for (int i = 0; i < numThread; i++) {
            threads[i] = new Thread(() -> {
                HelloController controller = context.getBean(HelloController.class);
                controller.test();
            });
        }

        for (int i = 0; i < numThread; i++) {
            threads[i].start();
        }
    }
}
