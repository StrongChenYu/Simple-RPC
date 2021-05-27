package com.csu.rpc.bean;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ServerConfigBean extends ConfigBean{


    /**
     * 服务器IP地址
     * rpc.server.ip
     * default: 127.0.0.1
     */
    public String ip;

    /**
     * 服务器端口号
     * rpc.server.port
     * default:8000
     */
    public Integer port;


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
