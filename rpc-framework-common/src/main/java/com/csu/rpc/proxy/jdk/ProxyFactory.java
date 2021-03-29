package com.csu.rpc.proxy.jdk;

import java.lang.reflect.Proxy;

public class ProxyFactory {

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                clazz.getInterfaces(),
                new SmsProxy()
        );
    }

    public static void main(String[] args) {
        SmsServiceImpl proxy = ProxyFactory.getProxy(SmsServiceImpl.class);
        proxy.send("陈宇");
    }
}
