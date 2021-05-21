package com.csu.rpc.client.handler;

import com.csu.rpc.dto.response.RpcResponse;
import com.csu.rpc.utils.SingletonFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private final UnProcessRequestsManager unProcessRequestsManager = SingletonFactory.getInstance(UnProcessRequestsManager.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse response) throws Exception {
        log.info("Client receive rpc response packet {}", response);
        unProcessRequestsManager.complete(response);
    }
}
