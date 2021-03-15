package com.csu.rpc.coder;

import com.csu.rpc.dto.Packet;
import com.csu.rpc.dto.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyKryoEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodeC.PACKETCODEC.encode(out, msg);
    }
}
