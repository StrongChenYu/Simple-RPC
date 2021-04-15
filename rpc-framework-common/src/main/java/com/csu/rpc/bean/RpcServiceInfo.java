package com.csu.rpc.bean;

import com.csu.rpc.constant.RpcConstants;
import lombok.*;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * @Author Chen Yu
 * @Date 2021/3/23 19:37
 */
@Getter
@Setter
@AllArgsConstructor
public class RpcServiceInfo {
    String serviceName;
    String group;
    String version;

    public RpcServiceInfo() {
        this.group = RpcConstants.DEFAULT_GROUP;
        this.version = RpcConstants.DEFAULT_VERSION;
    }

    public RpcServiceInfo(String serviceName) {
        this.serviceName = serviceName;
        this.group = RpcConstants.DEFAULT_GROUP;
        this.version = RpcConstants.DEFAULT_VERSION;
    }

    public String toRegisterRpcServiceName() {
        return String.format("%s/%s/%s", serviceName, group, version);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RpcServiceInfo that = (RpcServiceInfo) o;
        return Objects.equals(serviceName, that.serviceName) && Objects.equals(group, that.group) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceName, group, version);
    }
}


