package com.csu.rpc.config;


import lombok.Getter;

@Getter
public class CustomClientConfig {


    /**
     * 客户端连接失败的重试次数
     * rpc.client.maxRetry
     * 3(default)
     */
    Integer maxRetry;


    /**
     * 客户端的注册中心
     * rpc.client.registerCentral
     * 1.zookeeper(default)
     * 2.redis
     */
    String registerCentral;

    /**
     * 客户端选择服务端的算法
     * rpc.client.selectAddressAlgorithm
     * 1.random(default)
     * 2....
     */
    String selectAddressAlgorithm;


    /**
     * zookeeper的地址
     * rpc.client.zookeeperAddress
     * 如果使用zookeeper必须配置
     */
    String zookeeperAddress;

    /**
     * redis的地址
     * rpc.client.redisAddress
     * 如果使用redis必须配置
     */
    String redisAddress;


    /**
     * 客户端的编码解码方式
     * rpc.server.codeMode
     * 1.kryo(default)
     */
    String codecMode;

    /**
     * 客户端的压缩解压缩方式
     * rpc.server.compressMode
     * 2. gzip (default)
     */
    String compressMode;


    @Override
    public String toString() {
        return "CustomClientConfig{" +
                "maxRetry=" + maxRetry +
                ", registerCentral='" + registerCentral + '\'' +
                ", selectAddressAlgorithm='" + selectAddressAlgorithm + '\'' +
                ", zookeeperAddress='" + zookeeperAddress + '\'' +
                ", redisAddress='" + redisAddress + '\'' +
                ", codecMode='" + codecMode + '\'' +
                ", compressMode='" + compressMode + '\'' +
                '}';
    }
}
