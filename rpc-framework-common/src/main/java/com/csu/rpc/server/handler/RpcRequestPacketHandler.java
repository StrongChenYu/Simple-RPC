package com.csu.rpc.server.handler;

import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import com.csu.rpc.server.process.RpcRequestHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Chen Yu
 * @Date 2021/3/23 19:54
 */
@Slf4j
public class RpcRequestPacketHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private final RpcRequestHandler requestHandler = RpcRequestHandler.INSTANCE;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        log.info("Server receive rpc request packet: {}", request.toString());

        RpcResponse response = requestHandler.handleRpcRequest(request);

        Thread.sleep(5000);
        ctx.channel().writeAndFlush(response).addListener(future -> {
            log.info("Server return response packet successfully!");
        });
    }

}
