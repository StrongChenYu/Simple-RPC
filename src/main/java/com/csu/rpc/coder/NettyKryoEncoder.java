package com.csu.rpc.coder;

import com.csu.rpc.dto.serializer.KryoSerializer;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyKryoEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        byte[] serialize = KryoSerializer.INSTANCE.serialize(msg);
        int dataLength = serialize.length;
        int type = 0;

        if (msg instanceof RpcRequest) {
            type = 1;
        }

        if (msg instanceof RpcResponse) {
            type = 2;
        }

        out.writeInt(type);
        out.writeInt(dataLength);
        out.writeBytes(serialize);
    }
}
