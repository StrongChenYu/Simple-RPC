package com.csu.rpc.client;

import com.csu.rpc.client.handler.NettyClientHandler;
import com.csu.rpc.coder.NettyKryoDecoder;
import com.csu.rpc.coder.NettyKryoEncoder;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;

public class NettyClient {

    private final String host;
    private final int port;
    private static final Bootstrap bootstrap;


    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyKryoDecoder());
                        ch.pipeline().addLast(new NettyClientHandler());
                        ch.pipeline().addLast(new NettyKryoEncoder());
                    }
                });
    }

    public RpcResponse sendMessage(RpcRequest rpcRequest) {
        try {
            ChannelFuture sync = bootstrap.connect(host, port).sync();
            Channel futureChannel = sync.channel();

            if (futureChannel != null) {
                futureChannel.writeAndFlush(rpcRequest).addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("客户端发送消息：" + rpcRequest.toString());
                    }
                });
            }

            //futureChannel.closeFuture().sync();
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("RpcResponse");
            return futureChannel.attr(key).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        NettyClient client = new NettyClient("127.0.0.1", 8000);
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName("HelloService")
                .interfaceName("HelloService")
                .methodName("sayHello")
                .args(null)
                .argTypes(null).build();

        RpcResponse rpcResponse = client.sendMessage(rpcRequest);
        System.out.println(rpcResponse);
    }
}
