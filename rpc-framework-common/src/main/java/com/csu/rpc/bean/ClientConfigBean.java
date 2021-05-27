package com.csu.rpc.bean;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientConfigBean extends ConfigBean{


    /**
     * 客户端连接失败的重试次数
     * rpc.client.maxRetry
     * 3(default)
     */
    public Integer maxRetry;

    /**
     * 客户端选择服务端的算法
     * rpc.client.selectAddressAlgorithm
     * 1.random(default)
     * 2....
     */
    public String selectAddressAlgorithm;


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
