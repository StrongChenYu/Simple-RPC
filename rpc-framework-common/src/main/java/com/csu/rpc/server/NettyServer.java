package com.csu.rpc.server;

import com.csu.rpc.coder.NettyDecoder;
import com.csu.rpc.coder.NettyEncoder;
import com.csu.rpc.config.ServerRpcConfig;
import com.csu.rpc.server.handler.RpcRequestPacketHandler;
import com.csu.rpc.utils.SingletonFactory;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class NettyServer {

    private final ServerBootstrap bootstrap = new ServerBootstrap();
    private final ServerRpcConfig rpcConfig = SingletonFactory.getInstance(ServerRpcConfig.class);

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
                        ch.pipeline().addLast(new NettyDecoder());
                        ch.pipeline().addLast(new RpcRequestPacketHandler());
                        ch.pipeline().addLast(new NettyEncoder());
                    }
                });
    }

    /**
     * !!!!!!!!!!!just for test
     *
     * test最后会强制关闭jvm
     * 所以需要使用CountDownLatch等待服务器关闭
     */
//    public void start(CountDownLatch countDownLatch) {
//        try {
//            ChannelFuture f = bootstrap.bind(port).sync();
//            f.addListener(future -> {
//                if (future.isSuccess()) {
//                    Thread.sleep(1000);
//                    System.out.println(new Date() + "端口[" + port + "]绑定成功!");
//                    countDownLatch.countDown();
//                } else {
//                    System.err.println(new Date() + "端口[" + port + "]绑定失败!");
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void start(){
        int port = rpcConfig.getConfigBean().getPort();
        try {
            ChannelFuture f = bootstrap.bind(port).sync();
            f.addListener(future -> {
                if (future.isSuccess()) {
                    log.info("Server success to bind port {}", port);
                } else {
                    log.error("Server fail to bind port {}", port);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
