package com.csu.rpc.coder;

import com.csu.rpc.dto.Packet;
import com.csu.rpc.dto.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class NettyKryoDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Packet decode = PacketCodeC.PACKETCODEC.decode(in);
        out.add(decode);
    }
}
