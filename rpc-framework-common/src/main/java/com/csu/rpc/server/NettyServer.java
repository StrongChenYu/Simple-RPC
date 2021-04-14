package com.csu.rpc.server;

import com.csu.rpc.bean.RpcServiceInfo;
import com.csu.rpc.coder.NettyKryoDecoder;
import com.csu.rpc.coder.NettyKryoEncoder;
import com.csu.rpc.constant.RpcConstants;
import com.csu.rpc.server.handler.RpcRequestPacketHandler;
import com.csu.rpc.server.process.ServerProvider;
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
    private final Integer port = RpcConstants.DEFAULT_PORT;
    private final ServerProvider serverProvider = ServerProvider.INSTANCE;


    public NettyServer() {

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
                        ch.pipeline().addLast(new RpcRequestPacketHandler());
                        ch.pipeline().addLast(new NettyKryoEncoder());
                    }
                });
    }

    /**
     * just for test
     *
     * test最后会强制关闭jvm
     * 所以需要使用CountDownLatch等待服务器关闭
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

                    //这里扫描包并注册到providerService里面
                    //scanAddService();

                } else {
                    System.err.println(new Date() + "端口[" + port + "]绑定失败!");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 这个类主要负责
     * 将这个服务器里面所有提供服务的类注册进来
     */
    public void scanAddService(Object serviceImpl) {
        /**
         * ???????
         * 这里只传一个class可以调用吗???????
         */
        RpcServiceInfo rpcServiceInfo = new RpcServiceInfo();
        serverProvider.publishServer(serviceImpl, rpcServiceInfo);
    }

    /**
     * 服务类的启动函数
     * @param args
     */
    public static void main(String[] args) {
//        RpcRequestPacketHandler rpcRequestPacketHandler = new RpcRequestPacketHandler();
//        System.out.println(rpcRequestPacketHandler);
        NettyServer nettyServer = new NettyServer();
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
