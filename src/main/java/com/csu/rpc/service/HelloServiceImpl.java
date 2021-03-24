package com.csu.rpc.service;

/**
 * @Author Chen Yu
 * @Date 2021/3/22 20:23
 */
public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello() {
        return "hello world!";
    }
}
