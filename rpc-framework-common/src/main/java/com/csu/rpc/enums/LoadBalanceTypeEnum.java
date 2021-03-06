package com.csu.rpc.enums;

import com.csu.rpc.discovery.loadbalance.ConsistentHashBalance;
import com.csu.rpc.discovery.loadbalance.LoadBalance;
import com.csu.rpc.discovery.loadbalance.RandomLoadBalance;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Chen Yu
 * @Date 2021/4/8 20:49
 */
@AllArgsConstructor
@Getter
public enum LoadBalanceTypeEnum {

    RANDOM((byte)1, "random", RandomLoadBalance.class),
    CONSISTENT_HASH((byte)2, "consistentHash", ConsistentHashBalance.class);

    private Byte code;
    private String name;
    private Class<? extends LoadBalance> clazz;

}
