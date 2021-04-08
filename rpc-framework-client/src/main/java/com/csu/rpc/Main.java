package com.csu.rpc;

import com.csu.rpc.annotation.RpcReference;
import com.csu.rpc.controller.HelloController;
import com.csu.rpc.proxy.RpcClientProxy;
import com.csu.rpc.service.HelloService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @Author Chen Yu
 * @Date 2021/3/25 20:07
 */
public class Main {

    public static void main(String[] args) throws NoSuchFieldException {
        RpcClientProxy proxy = new RpcClientProxy();
        HelloService helloService = proxy.getProxy(HelloService.class);
        helloService.sayHello();

//        Field[] declaredFields = HelloController.class.getDeclaredFields();
//        for (Field field : declaredFields) {
//            System.out.println(field.getName());
//        }
//
//        Field helloService = HelloController.class.getDeclaredField("helloService");
//        RpcTest annotation = helloService.getAnnotation(RpcTest.class);
//        System.out.println(annotation);
    }
}
