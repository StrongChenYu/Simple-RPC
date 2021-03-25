package com.csu.rpc.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SmsProxy implements InvocationHandler {

    private Object object;

    public SmsProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before method!");
        Object result = method.invoke(object, args);
        System.out.println("after method!");
        return result;
    }
}
