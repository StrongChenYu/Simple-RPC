package com.csu.rpc.service;

import com.csu.rpc.annotation.RpcService;

/**
 * @Author Chen Yu
 * @Date 2021/3/22 20:23
 */
@RpcService
public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello() {
        return "hello world!";
    }
}
