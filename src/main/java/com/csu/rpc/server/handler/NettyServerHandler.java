package com.csu.rpc.server.handler;

import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;

import io.netty.channel.*;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest rpcRequest = (RpcRequest) msg;

        System.out.println("服务端收到消息：" + rpcRequest.toString());
        RpcResponse response = RpcResponse.builder().message("服务端回复的消息").build();

        ChannelFuture f = ctx.channel().writeAndFlush(response);
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("服务端异常");
        ctx.close();
    }
}
