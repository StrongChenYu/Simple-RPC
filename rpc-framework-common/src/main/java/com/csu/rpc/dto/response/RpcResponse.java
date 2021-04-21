package com.csu.rpc.dto.response;

import com.csu.rpc.dto.Command;
import com.csu.rpc.dto.Packet;
import com.csu.rpc.enums.RpcResponseMessageEnum;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class RpcResponse extends Packet {

    /**
     * 标志着是那个RpcRequest的结果
     */
    private String requestId;

    /**
     * 调用服务的情况，code
     */
    private Integer code;

    /**
     * 调用服务的是否有错的信息
     */
    private String message;

    /**
     * 返回的结果
     */
    private Object data;

    public static RpcResponse SUCCESS(String requestId, Object res) {
        return RpcResponse.builder()
                .requestId(requestId)
                .code(RpcResponseMessageEnum.SUCCESS.getCode())
                .message(RpcResponseMessageEnum.SUCCESS.getMessage())
                .data(res)
                .build();
    }

    public static RpcResponse ERROR(String requestId, Integer code, String message) {
        return RpcResponse.builder()
                .requestId(requestId)
                .code(code)
                .message(message)
                .build();
    }

    public static RpcResponse SERVICE_NOT_FIND(String requestId) {
        return ERROR(
                requestId,
                RpcResponseMessageEnum.SERVICE_NOT_FIND.getCode(),
                RpcResponseMessageEnum.SERVICE_NOT_FIND.getMessage());
    }

    public static RpcResponse INVOKE_ERROR(String requestId) {
        return ERROR(
                requestId,
                RpcResponseMessageEnum.INVOKE_FAIL.getCode(),
                RpcResponseMessageEnum.INVOKE_FAIL.getMessage());
    }

    @Override
    public Byte serializerType() {
        return Command.RPC_RESPONSE_PACKET;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "requestId='" + requestId + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

}
