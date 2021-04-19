package com.csu.rpc.client.handler;

import com.csu.rpc.dto.response.RpcResponse;
import com.csu.rpc.utils.SingletonFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private UnProcessRequestsManager unProcessRequestsManager = SingletonFactory.getInstance(UnProcessRequestsManager.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse response) throws Exception {
        System.out.println("收到服务端发来的消息" + response);
        unProcessRequestsManager.complete(response);
    }
}
