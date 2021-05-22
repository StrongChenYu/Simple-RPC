package com.csu.rpc.proxy;

import com.csu.rpc.bean.RpcServiceInfo;
import com.csu.rpc.client.NettyClient;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import com.csu.rpc.utils.SingletonFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @Author Chen Yu
 * @Date 2021/3/29 19:43
 */
@Slf4j
public class RpcClientProxy implements InvocationHandler {

    private RpcServiceInfo serviceInfo;

    public RpcClientProxy(RpcServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
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
        NettyClient nettyClient = SingletonFactory.getInstance(NettyClient.class);

        String serviceName = serviceInfo.getServiceName();
        //服务名默认为method.getDeclaringClass().getSimpleName()
        if (serviceName == null || serviceName.equals("")) {
            serviceName = method.getDeclaringClass().getSimpleName();
        }

        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .requestId(UUID.randomUUID().toString())
                .group(serviceInfo.getGroup())
                .version(serviceInfo.getVersion())
                .args(args)
                .argTypes(method.getParameterTypes())
                .build();


        RpcResponse response = nettyClient.sendMessage(rpcRequest);


        return response.getData();
    }

}
