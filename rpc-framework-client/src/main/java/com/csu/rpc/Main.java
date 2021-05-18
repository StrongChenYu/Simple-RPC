package com.csu.rpc;

import com.csu.rpc.annotation.RpcReference;
import com.csu.rpc.annotation.RpcScan;
import com.csu.rpc.controller.HelloController;
import com.csu.rpc.proxy.RpcClientProxy;
import com.csu.rpc.service.HelloService;
import com.csu.rpc.spring.CustomClientConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @Author Chen Yu
 * @Date 2021/3/25 20:07
 */
@RpcScan(basePackage = {"com.csu.rpc"})
public class Main {
    public static void main(String[] args) throws NoSuchFieldException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
//        HelloController controller = context.getBean(HelloController.class);
//        controller.test();
        CustomClientConfig bean = context.getBean(CustomClientConfig.class);
        System.out.println(bean);
    }
}
