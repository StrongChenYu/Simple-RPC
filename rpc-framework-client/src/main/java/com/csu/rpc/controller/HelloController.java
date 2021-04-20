package com.csu.rpc.controller;

import com.csu.rpc.annotation.RpcReference;
import com.csu.rpc.service.HelloService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * @Author Chen Yu
 * @Date 2021/3/29 20:35
 */
@Controller
public class HelloController {

    @RpcReference
    HelloService helloService;

    public void test() {
        System.out.println("before invoke Hello Controller method [test]");
        helloService.sayHello();
        System.out.println("after invoke Hello Controller method [test]");
    }
}
