package com.csu.rpc.dto.response;

import com.csu.rpc.dto.Command;
import com.csu.rpc.dto.Packet;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@ToString
public class RpcResponse extends Packet {
    private Object message;

    @Override
    public Byte serializerType() {
        return Command.RPC_RESPONSE_PACKET;
    }
}
