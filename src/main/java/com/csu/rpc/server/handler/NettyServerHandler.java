package com.csu.rpc.server.handler;


import io.netty.channel.*;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        System.out.println("收到消息" + msg);
    }
}
