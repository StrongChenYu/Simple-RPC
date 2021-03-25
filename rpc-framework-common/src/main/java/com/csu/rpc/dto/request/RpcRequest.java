package com.csu.rpc.dto.request;

import com.csu.rpc.dto.Command;
import com.csu.rpc.dto.Packet;
import lombok.*;

import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@ToString
public class RpcRequest extends Packet {

    /**
     * 服务的名字
     */
    private String serviceName;

    /**
     * 请求的接口的名字
     */
    private String interfaceName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RpcRequest that = (RpcRequest) o;
        return Objects.equals(interfaceName, that.interfaceName) && Objects.equals(methodName, that.methodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interfaceName, methodName);
    }

    @Override
    public Byte serializerType() {
        return Command.RPC_REQUEST_PACKET;
    }
}
