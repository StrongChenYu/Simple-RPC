package com.csu.rpc.discovery.loadbalance;

import com.csu.rpc.config.RpcConfig;
import com.csu.rpc.enums.LoadBalanceTypeEnum;
import com.csu.rpc.utils.SingletonFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoadBalanceContext implements LoadBalance{

    private LoadBalance loadBalance;


    public void setLoadBalance(LoadBalance loadBalance) {
        this.loadBalance = loadBalance;
    }

    @Override
    public String selectServer(List<String> serverList, String serviceName) {
        return loadBalance.selectServer(serverList, serviceName);
    }
}
