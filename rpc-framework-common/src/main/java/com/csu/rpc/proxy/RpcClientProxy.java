package com.csu.rpc.proxy;

import com.csu.rpc.bean.RemoteServiceProperties;
import com.csu.rpc.client.NettyClient;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @Author Chen Yu
 * @Date 2021/3/29 19:43
 */
public class RpcClientProxy implements InvocationHandler {

    private RemoteServiceProperties serviceProperties;

    public RpcClientProxy(RemoteServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    public RpcClientProxy() {
        this.serviceProperties = new RemoteServiceProperties();
    }

    /**
     * 返回代理类
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        NettyClient nettyClient = new NettyClient();

        String interfaceName = method.getDeclaringClass().getSimpleName();

        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(interfaceName)
                .methodName(method.getName())
                .requestId(UUID.randomUUID().toString())
                .group(serviceProperties.getGroup())
                .version(serviceProperties.getVersion())
                .args(args)
                .argTypes(method.getParameterTypes())
                .build();


        RpcResponse response = nettyClient.sendMessage(rpcRequest);

        return response.getData();
    }

}
