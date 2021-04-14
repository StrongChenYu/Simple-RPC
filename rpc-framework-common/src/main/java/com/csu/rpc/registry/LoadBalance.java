package com.csu.rpc.registry;

import com.csu.rpc.registry.impl.RandomLoadBalance;

import java.util.List;

/**
 * @Author Chen Yu
 * @Date 2021/4/7 19:33
 */
public interface LoadBalance {
    LoadBalance INSTANCE = new RandomLoadBalance();

    String selectServer(List<String> serverList, String serviceName);
}
