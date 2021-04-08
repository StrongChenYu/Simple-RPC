package com.csu.rpc.enums;

import com.csu.rpc.registry.ServiceRegistry;
import com.csu.rpc.registry.impl.RedisRegistry;
import com.csu.rpc.registry.impl.ZookeeperRegistry;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Chen Yu
 * @Date 2021/4/8 20:39
 */
@AllArgsConstructor
@Getter
public enum RegistryTypeEnum {

    ZOOKEEPER((byte)1, "zookeeper", ZookeeperRegistry.class),
    REDIS((byte)2, "redis", RedisRegistry.class);

    private Byte code;
    private String name;
    private Class<? extends ServiceRegistry> clazz;

}
