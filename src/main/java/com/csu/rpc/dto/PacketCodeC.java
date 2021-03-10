package com.csu.rpc.dto;

import com.csu.rpc.dto.protocol.ProtocolStruct;
import io.netty.buffer.ByteBuf;

public class PacketCodeC {

    public static final byte[] MAGIC_NUMBER = {(byte)'j', (byte)'r', (byte)'p', 'c'};

    public Packet decode(ByteBuf byteBuf) {

        ProtocolStruct decodeStruct = ProtocolStruct.decode(byteBuf);

        return null;
    }

    public void encode(ByteBuf byteBuf, Packet packet) {

    }

}
