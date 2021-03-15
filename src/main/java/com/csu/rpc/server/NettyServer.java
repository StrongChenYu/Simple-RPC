package com.csu.rpc.server;

import com.csu.rpc.coder.NettyKryoDecoder;
import com.csu.rpc.coder.NettyKryoEncoder;
import com.csu.rpc.server.handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Date;

public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.SO_BACKLOG, 128)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyKryoDecoder());
                        ch.pipeline().addLast(new NettyKryoEncoder());
                        ch.pipeline().addLast(new NettyServerHandler());
                    }
                });

        bind(bootstrap, 8080);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) throws InterruptedException {
//        serverBootstrap.bind(port).addListener(future -> {
//            if (future.isSuccess()) {
//                System.out.println(new Date() + "端口[" + port + "]绑定成功!");
//            } else {
//                System.err.println(new Date() + "端口[" + port + "]绑定失败!");
//            }
//        });
        serverBootstrap.bind(port).sync();
        System.out.println("绑定端口失败！");
    }

}
