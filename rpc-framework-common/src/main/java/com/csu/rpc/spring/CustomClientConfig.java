package com.csu.rpc.spring;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:client.properties")
@Getter
public class CustomClientConfig {


    /**
     * 客户端连接失败的重试次数
     * rpc.client.maxRetry
     * 3(default)
     */
    @Value("${rpc.client.maxRetry:3}")
    Integer maxRetry;

    /**
     * 客户端的注册中心
     * rpc.client.registerCentral
     * 1.zookeeper(default)
     * 2.redis
     */
    @Value("${rpc.client.registerCentral:zookeeper}")
    String clientRegisterCentral;

    /**
     * 客户端选择服务端的算法
     * rpc.client.selectAddressAlgorithm
     * 1.random(default)
     * 2....
     */
    @Value("${rpc.client.selectAddressAlgorithm:random}")
    String remoteAddressSelectAlgorithm;


    /**
     * zookeeper的地址
     * rpc.client.zookeeperAddress
     * 如果使用zookeeper必须配置
     */
    @Value("${rpc.client.zookeeperAddress}")
    String zookeeperAddress;

    /**
     * redis的地址
     * rpc.client.redisAddress
     * 如果使用redis必须配置
     */
    @Value("${rpc.client.redisAddress}")
    String redisAddress;

    @Override
    public String toString() {
        return "CustomClientConfig{" +
                "maxRetry=" + maxRetry +
                ", clientRegisterCentral='" + clientRegisterCentral + '\'' +
                ", remoteAddressSelectAlgorithm='" + remoteAddressSelectAlgorithm + '\'' +
                ", zookeeperAddress='" + zookeeperAddress + '\'' +
                ", redisAddress='" + redisAddress + '\'' +
                '}';
    }
}
