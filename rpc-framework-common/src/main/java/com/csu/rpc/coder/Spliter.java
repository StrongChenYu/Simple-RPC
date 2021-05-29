package com.csu.rpc.coder;

import com.csu.rpc.constant.RpcConstants;
import com.csu.rpc.dto.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.Arrays;

public class Spliter extends LengthFieldBasedFrameDecoder {

    private static final int LENGTH_FIELD_OFFSET = 12;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (!validate(in)) {
            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, in);
    }

    private boolean validate(ByteBuf byteBuf) {
        byte[] actual = new byte[4];
        byteBuf.getBytes(byteBuf.readerIndex(), actual);

        return Arrays.equals(actual, RpcConstants.MAGIC_NUMBER);
    }

    public static void main(String[] args) {
        byte[] bytes = new byte[]{'A','A'};
        byte[] bytes1 = new byte[]{'A','A'};

        System.out.println(bytes == bytes1);
        boolean equals = Arrays.equals(bytes, bytes1);
        System.out.println(equals);
    }
}
