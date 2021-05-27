package com.csu.rpc.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigBean {

    /**
     * 客户端的注册中心
     * rpc.client.registerCentral
     * 1.zookeeper(default)
     * 2.redis
     */
    public String registerCentral;

    /**
     * zookeeper的地址
     * rpc.client.zookeeperAddress
     * 如果使用zookeeper必须配置
     */
    public String zookeeperAddress;

    /**
     * redis的地址
     * rpc.client.redisAddress
     * 如果使用redis必须配置
     */
    public String redisAddress;


    /**
     * 客户端的编码解码方式
     * rpc.server.codeMode
     * 1.kryo(default)
     */
    public String codecMode;

    /**
     * 客户端的压缩解压缩方式
     * rpc.server.compressMode
     * 2. gzip (default)
     */
    public String compressMode;

}
