package com.csu.rpc.service;

import com.csu.rpc.annotation.RpcService;

/**
 * @Author Chen Yu
 * @Date 2021/3/22 20:23
 */
@RpcService(version = 2)
public class HelloServiceImpl implements HelloService{
    @Override
    public int sayHello() {
        return 1;
    }
}
