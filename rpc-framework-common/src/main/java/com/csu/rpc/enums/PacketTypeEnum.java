package com.csu.rpc.enums;

import com.csu.rpc.dto.Packet;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @Author Chen Yu
 * @Date 2021/4/8 19:41
 */
@Getter
@AllArgsConstructor
public enum PacketTypeEnum {

    RPC_RESPONSE_PACKET((byte)1, RpcRequest.class),
    RPC_REQUEST_PACKET((byte)2, RpcResponse.class);

    private byte code;
    private Class<? extends Packet> clazz;

    public static Class<? extends Packet> getPacketClassByCode(Byte code) {
        for (PacketTypeEnum packetTypeEnum : PacketTypeEnum.values()) {
            if (packetTypeEnum.getCode() == code) {
                return packetTypeEnum.getClazz();
            }
        }
        return null;
    }
}
