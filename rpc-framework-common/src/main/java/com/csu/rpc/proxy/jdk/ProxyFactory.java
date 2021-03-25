package com.csu.rpc.proxy.jdk;

import java.lang.reflect.Proxy;

public class ProxyFactory {

    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new SmsProxy(target)
        );
    }

    public static void main(String[] args) {
        SmsService proxy = (SmsService) ProxyFactory.getProxy(new SmsServiceImpl());

        proxy.send("陈宇");
    }
}
