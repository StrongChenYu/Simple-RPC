package com.csu.rpc.loadBalance;

import com.csu.rpc.discovery.loadbalance.ConsistentHashBalance;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LoadBalanceTest {


    @Test
    public void consistentHashLoadBalanceTest() {
        ConsistentHashBalance consistentHashBalance = new ConsistentHashBalance();

        List<String> serverList = new ArrayList<>();
        for (int i = 0; i < 100; i ++) {
            String ip = "192.168.0." + i;
            serverList.add(ip);
        }

        String test = consistentHashBalance.selectServer(serverList, "test2");
        System.out.println(test);
    }
}
