package com.csu.rpc.client;

import com.csu.rpc.bean.RpcServiceInfo;
import com.csu.rpc.client.handler.NettyClientHandler;
import com.csu.rpc.client.handler.UnProcessRequestsManager;
import com.csu.rpc.coder.NettyKryoDecoder;
import com.csu.rpc.coder.NettyKryoEncoder;
import com.csu.rpc.constant.RpcConstants;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import com.csu.rpc.registry.ServerDiscovery;
import com.csu.rpc.spring.RpcConfig;
import com.csu.rpc.utils.SingletonFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class NettyClient {
    private final Bootstrap bootstrap;
    private final ServerDiscovery serverDiscovery = ServerDiscovery.INSTANCE;
    private final UnProcessRequestsManager unProcessRequestsManager = SingletonFactory.getInstance(UnProcessRequestsManager.class);
    private final EventLoopGroup eventLoopGroup;

    @Autowired
    RpcConfig rpcConfig;

    public NettyClient() {
        eventLoopGroup = new NioEventLoopGroup();
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


    public Channel doConnect(InetSocketAddress inetSocketAddress) throws ExecutionException, InterruptedException {
        CompletableFuture<Channel> connectFuture = new CompletableFuture<>();
        connect(bootstrap, inetSocketAddress, 1, connectFuture);
        return connectFuture.get();
    }

    private void connect(Bootstrap bootstrap, InetSocketAddress address, int retry, CompletableFuture<Channel> connectFuture) {
        int max_retry = rpcConfig.getClientConfig().getMaxRetry();
        bootstrap.connect(address).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                log.info("Client connected server {} successful!", address.toString());
                connectFuture.complete(future.channel());
            } else if (retry == 0) {
                log.error("Client failed in {} attempts to connect and gave up to connect server {}", max_retry, address);
                connectFuture.completeExceptionally(future.cause());
            } else {
                // 第几次重连
                int order = (max_retry - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                log.warn("Client failed to connect server and retry");
                bootstrap.config().group().schedule(() -> connect(bootstrap, address, retry - 1, connectFuture), delay, TimeUnit
                        .SECONDS);
            }
        });
    }

    public RpcResponse sendMessage(RpcRequest rpcRequest) {
        CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
        RpcServiceInfo serviceInfo = getRpcServiceInfo(rpcRequest);

        //这里要将rpcRequest中的group和version提取出来，然后再去搜索
        InetSocketAddress address = serverDiscovery.lookupServer(serviceInfo);

        /**
         * 连接服务端
         */
        Channel futureChannel = null;
        try {
            futureChannel = doConnect(address);
        } catch (InterruptedException | ExecutionException e) {
            //连接不成功就直接关掉
            eventLoopGroup.shutdownGracefully();
            e.printStackTrace();
        }

        /**
         * 如果连接成功就发送请求
         */
        if (futureChannel != null) {
            futureChannel.writeAndFlush(rpcRequest).addListener(future -> {
                if (future.isSuccess()) {
                    unProcessRequestsManager.addUnProcessRequest(rpcRequest.getRequestId(), responseFuture);
                    log.info("Client send rpc request packet {}", rpcRequest.toString());
                }
            });
        }

        try {
            //同步超时机制
            return responseFuture.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Response Packet receive fail !");

            e.printStackTrace();
            eventLoopGroup.shutdownGracefully();
        } catch (TimeoutException e) {
            log.error("Time out to receive response packet !");
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
