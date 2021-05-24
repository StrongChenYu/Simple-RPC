package com.csu.rpc.discovery.loadbalance;

import com.csu.rpc.discovery.loadbalance.LoadBalance;

import java.util.List;

/**
 * @Author Chen Yu
 * @Date 2021/4/7 19:38
 */
public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public String selectServer(List<String> serverList, String serviceName) {
        if (serverList == null || serverList.size() == 0) {
            return null;
        }

        if (serverList.size() == 1) return serverList.get(0);

        return doSelect(serverList, serviceName);
    }

    protected abstract String doSelect(List<String> serverList, String serviceName);

}
