package com.csu.rpc.proxy;

import com.csu.rpc.client.NettyClient;
import com.csu.rpc.dto.request.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author Chen Yu
 * @Date 2021/3/29 19:43
 */
public class RpcClientProxy implements InvocationHandler {

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
        NettyClient nettyClient = new NettyClient("127.0.0.1", 8000);

        String interfaceName = method.getDeclaringClass().getCanonicalName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(interfaceName)
                .interfaceName(interfaceName)
                .methodName(method.getName())
                .args(null)
                .argTypes(null).build();

        nettyClient.sendMessage(rpcRequest);

        return null;
    }

    public static void main(String[] args) {
        System.out.println(RpcClientProxy.class.getCanonicalName());
    }
}
