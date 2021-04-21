package com.csu.rpc.dto.request;

import com.csu.rpc.dto.Command;
import com.csu.rpc.dto.Packet;
import lombok.*;

import java.util.Arrays;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class RpcRequest extends Packet {

    /**
     * 服务的名字
     */
    private String serviceName;

    /**
     * 请求的接口中的方法名字
     */
    private String methodName;

    /**
     * 请求中的方法中的所有参数
     */
    private Object[] args;

    /**
     * 请求中的方法中所有参数的类型
     * 通过class.cast方法转化为对应的类型
     */
    private Class<?>[] argTypes;

    /**
     * 版本控制
     */
    private String version;

    /**
     * 服务所属的组
     */
    private String group;

    /**
     * 请求的标号
     */
    private String requestId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RpcRequest that = (RpcRequest) o;
        return Objects.equals(serviceName, that.serviceName) && Objects.equals(methodName, that.methodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceName, methodName);
    }

    @Override
    public Byte serializerType() {
        return Command.RPC_REQUEST_PACKET;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", version='" + version + '\'' +
                ", group='" + group + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
