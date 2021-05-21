package com.csu.rpc.config;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomServerConfig {


    /**
     * 服务器IP地址
     * rpc.server.ip
     * default: 127.0.0.1
     */
    String ip;

    /**
     * 服务器端口号
     * rpc.server.port
     * default:8000
     */
    Integer port;

    /**
     * 服务器使用的注册中心
     * rpc.server.registerCentral
     * 1. zookeeper(default)
     * 2. redis
     */
    String registerCentral;


    /**
     * 如果是zookeeper
     * 地址是多少
     * rpc.server.zookeeperAddress
     * 如果配置为zookeeper, 必须配置项
     */
    String zookeeperAddress;

    /**
     * 如果是redis
     * 地址是多少
     * rpc.server.redisAddress
     * 如果配置为redis，必须配置项
     */
    String redisAddress;

    /**
     * 服务端的编码解码方式
     * rpc.server.codeMode
     * 1.kryo(default)
     */
    String codecMode;

    /**
     * 服务端的压缩解压缩方式
     * rpc.server.compressMode
     * 2. gzip (default)
     */
    String compressMode;


    public String getZookeeperIP() {
        return zookeeperAddress.split(":")[0];
    }

    public int getZookeeperPort() {
        return Integer.parseInt(zookeeperAddress.split(":")[1]);
    }

    @Override
    public String toString() {
        return "CustomServerConfig{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", registerCentral='" + registerCentral + '\'' +
                ", zookeeperAddress='" + zookeeperAddress + '\'' +
                ", redisAddress='" + redisAddress + '\'' +
                ", codecMode='" + codecMode + '\'' +
                ", compressMode='" + compressMode + '\'' +
                '}';
    }
}
