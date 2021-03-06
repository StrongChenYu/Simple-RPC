package com.csu.rpc.controller;

import com.csu.rpc.annotation.RpcReference;
import com.csu.rpc.service.HelloService;
import org.springframework.stereotype.Controller;

/**
 * @Author Chen Yu
 * @Date 2021/3/29 20:35
 */
@Controller
public class HelloController {

    @RpcReference(version = 2)
    HelloService helloService;

    public int test() {
        return helloService.sayHello();
    }
}
