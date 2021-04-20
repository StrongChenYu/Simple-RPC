package com.csu.rpc.client;

import com.csu.rpc.bean.RpcServiceInfo;
import com.csu.rpc.client.handler.NettyClientHandler;
import com.csu.rpc.client.handler.UnProcessRequestsManager;
import com.csu.rpc.coder.NettyKryoDecoder;
import com.csu.rpc.coder.NettyKryoEncoder;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import com.csu.rpc.registry.ServerDiscovery;
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
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class NettyClient {
    private static final Bootstrap bootstrap;
    private final ServerDiscovery serverDiscovery = ServerDiscovery.INSTANCE;
    private final UnProcessRequestsManager unProcessRequestsManager = SingletonFactory.getInstance(UnProcessRequestsManager.class);

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
        CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
        RpcServiceInfo serviceInfo = getRpcServiceInfo(rpcRequest);

        //这里要将rpcRequest中的group和version提取出来，然后再去搜索
        InetSocketAddress address = serverDiscovery.lookupServer(serviceInfo);

        try {
            ChannelFuture sync = bootstrap.connect(address.getHostName(), address.getPort()).sync();
            Channel futureChannel = sync.channel();

            if (futureChannel != null) {
                futureChannel.writeAndFlush(rpcRequest).addListener(future -> {
                    if (future.isSuccess()) {

                        unProcessRequestsManager.addUnProcessRequest(rpcRequest.getRequestId(), responseFuture);
                        System.out.println("客户端发送消息：" + rpcRequest.toString());
                    }
                });
            }

            return responseFuture.get();
        } catch (InterruptedException | ExecutionException e) {
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
