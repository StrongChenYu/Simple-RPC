package com.csu.rpc.client;

import com.csu.rpc.bean.RpcServiceInfo;
import com.csu.rpc.client.handler.NettyClientHandler;
import com.csu.rpc.coder.NettyKryoDecoder;
import com.csu.rpc.coder.NettyKryoEncoder;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import com.csu.rpc.registry.ServerDiscovery;
import com.csu.rpc.registry.impl.ZkServerDiscovery;
import com.csu.rpc.utils.SingletonFactory;
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

import java.net.InetSocketAddress;

public class NettyClient {


    private static final Bootstrap bootstrap;
    private ServerDiscovery serverDiscovery = ServerDiscovery.INSTANCE;


    public NettyClient() {}

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

        RpcServiceInfo serviceInfo = getRpcServiceInfo(rpcRequest);

        //这里要将rpcRequest中的group和version提取出来，然后再去搜索
        InetSocketAddress address = serverDiscovery.lookupServer(serviceInfo);

        try {
            ChannelFuture sync = bootstrap.connect(address.getHostName(), address.getPort()).sync();
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

    /**
     * 将rpc request包中的信息转化成
     * 相应的信息
     * @param rpcRequest
     * @return
     */
    private RpcServiceInfo getRpcServiceInfo(RpcRequest rpcRequest) {
        RpcServiceInfo serviceInfo = new RpcServiceInfo(rpcRequest.getServiceName(), rpcRequest.getGroup(), rpcRequest.getVersion());
        return serviceInfo;
    }
}
