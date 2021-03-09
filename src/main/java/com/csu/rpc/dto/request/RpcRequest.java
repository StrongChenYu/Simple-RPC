package com.csu.rpc.dto.request;

import lombok.*;

import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@ToString
public class RpcRequest {
    private String interfaceName;
    private String methodName;

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
}
