package com.csu.rpc.server;

import com.csu.rpc.coder.NettyKryoDecoder;
import com.csu.rpc.coder.NettyKryoEncoder;
import com.csu.rpc.server.handler.NettyServerHandler;
import io.netty.bootstrap.Bootstrap;
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
import java.util.concurrent.CountDownLatch;

public class NettyServer {

    private final ServerBootstrap bootstrap = new ServerBootstrap();
    private final int port;

    public NettyServer(int port) {
        this.port = port;

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
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
    }

    /**
     * just for test
     * @param countDownLatch
     */
    public void start(CountDownLatch countDownLatch) {
        try {
            ChannelFuture f = bootstrap.bind(port).sync();
            f.addListener(future -> {
                if (future.isSuccess()) {
                    Thread.sleep(1000);
                    System.out.println(new Date() + "端口[" + port + "]绑定成功!");
                    countDownLatch.countDown();
                } else {
                    System.err.println(new Date() + "端口[" + port + "]绑定失败!");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(){
        try {
            ChannelFuture f = bootstrap.bind(port).sync();

            f.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println(new Date() + "端口[" + port + "]绑定成功!");
                } else {
                    System.err.println(new Date() + "端口[" + port + "]绑定失败!");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer(8080);
        nettyServer.start();
    }

//    private void bind() {
//        try {
//            ChannelFuture sync = bootstrap.bind(port).sync();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//    private void bind(){
//        ChannelFuture channelFuture = bootstrap.bind(port).addListener(future -> {
//            if (future.isSuccess()) {
//                System.out.println(new Date() + "端口[" + port + "]绑定成功!");
//            } else {
//                System.err.println(new Date() + "端口[" + port + "]绑定失败!");
//            }
//        });
//
//        try {
//            //给老子等着
//            channelFuture.sync();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

}
