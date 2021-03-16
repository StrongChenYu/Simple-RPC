package com.csu.rpc.server.handler;

import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;

import io.netty.channel.*;

public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        System.out.println("服务端收到消息：" + msg.toString());
        RpcResponse response = RpcResponse.builder().message("服务端回复的消息").build();
        ChannelFuture f = ctx.channel().writeAndFlush(response);
        f.addListener(ChannelFutureListener.CLOSE);
    }

}
