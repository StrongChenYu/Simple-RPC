package com.csu.rpc.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author Chen Yu
 * @Date 2021/3/24 20:00
 */
public class LoggerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到消息");
        super.channelRead(ctx, msg);
    }
}
