package com.csu.rpc.spring;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:server.properties")
@Getter
public class CustomServerConfig {


    /**
     * 服务器IP地址
     * rpc.server.ip
     * default: 127.0.0.1
     */
    @Value("${rpc.server.ip:127.0.0.1}")
    String serverIP;

    /**
     * 服务器端口号
     * rpc.server.port
     * default:8000
     */
    @Value("${rpc.server.port:8000}")
    Integer serverPort;

    /**
     * 服务器使用的注册中心
     * rpc.server.registerCentral
     * 1. zookeeper(default)
     * 2. redis
     */
    @Value("${rpc.server.registerCentral:zookeeper}")
    String serverRegisterCentral;


    /**
     * 如果是zookeeper
     * 地址是多少
     * rpc.server.zookeeperAddress
     * 如果配置为zookeeper, 必须配置项
     */
    @Value("${rpc.server.zookeeperAddress}")
    String zookeeperAddress;

    /**
     * 如果是redis
     * 地址是多少
     * rpc.server.redisAddress
     * 如果配置为redis，必须配置项
     */
    @Value("${rpc.server.redisAddress}")
    String redisAddress;

    /**
     * 服务端的编码解码方式
     * rpc.server.codeMode
     * 1.kryo(default)
     */
    @Value("${rpc.server.codeMode:kryo}")
    String codecMode;

    /**
     * 客户端的编码解码方式
     * rpc.server.compressMode
     * 2. gzip (default)
     */
    @Value("${rpc.server.compressMode:gzip}")
    String compressMode;
}
