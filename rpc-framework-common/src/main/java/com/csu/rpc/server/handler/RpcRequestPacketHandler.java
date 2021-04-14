package com.csu.rpc.server.handler;

import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import com.csu.rpc.server.process.RpcRequestHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author Chen Yu
 * @Date 2021/3/23 19:54
 */
public class RpcRequestPacketHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private final RpcRequestHandler requestHandler = RpcRequestHandler.INSTANCE;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        System.out.println("服务端收到消息：" + request.toString());

        RpcResponse response = requestHandler.handleRpcRequest(request);
        ctx.channel().writeAndFlush(response);
    }

}
