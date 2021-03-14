package com.csu.rpc.coder;

import com.csu.rpc.dto.serializer.KryoSerializer;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class NettyKryoDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int type = in.readInt();
        int dataLength = in.readInt();

        System.out.println("收到的数据长度为：" + dataLength);
        byte[] serialize = new byte[dataLength];
        in.readBytes(serialize);

        if (type == 1) {
            RpcRequest deserialize = KryoSerializer.INSTANCE.deserialize(serialize, RpcRequest.class);
            out.add(deserialize);
        } else if (type == 2) {
            RpcResponse deserialize = KryoSerializer.INSTANCE.deserialize(serialize, RpcResponse.class);
            out.add(deserialize);
        }
    }
}
