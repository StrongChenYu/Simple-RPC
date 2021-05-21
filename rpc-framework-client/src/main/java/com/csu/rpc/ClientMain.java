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
    public static void main(String[] args) throws NoSuchFieldException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientMain.class);
        HelloController controller = context.getBean(HelloController.class);
        controller.test();
    }
}
