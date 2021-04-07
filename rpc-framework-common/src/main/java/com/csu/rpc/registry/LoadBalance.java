package com.csu.rpc.registry;

import java.util.List;

/**
 * @Author Chen Yu
 * @Date 2021/4/7 19:33
 */
public interface LoadBalance {
    String selectServer(List<String> serverList, String serviceName);
}
