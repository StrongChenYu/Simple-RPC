package com.csu.rpc.bean;

import lombok.*;

/**
 * @Author Chen Yu
 * @Date 2021/3/30 20:37
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class RemoteServiceProperties {

    String version;
    String group;
    String serviceName;

    public RemoteServiceProperties() {
        version = "";
        group = "";
    }

    public String toRpcServiceName() {
        return this.getServiceName() + this.getGroup() + this.getVersion();
    }
}
