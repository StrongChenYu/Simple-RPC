package com.csu.rpc.coder;

import com.csu.rpc.serializer.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyKryoEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        byte[] serialize = KryoSerializer.INSTANCE.serialize(msg);
        int dataLength = serialize.length;

        out.writeInt(dataLength);
        out.writeBytes(serialize);
    }
}
