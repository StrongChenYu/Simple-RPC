package com.csu.rpc.server.process.processImpl;

import com.csu.rpc.server.process.InvokeHandler;
import com.csu.rpc.utils.SingletonFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author Chen Yu
 * @Date 2021/3/23 20:05
 */
public class InvokeHandlerImpl implements InvokeHandler {

    private final InvokeHandler invokeHandler = SingletonFactory.getInstance(InvokeHandlerImpl.class);

    @Override
    public Object invokeMethod(Object service, String methodName, Object[] args, Class<?>[] argTypes) {
        Object res = null;
        try {
            Method method = service.getClass().getMethod(methodName, argTypes);
            res = method.invoke(service, args);
            System.out.println("invoke method[" + methodName + "]from Object" + service);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return res;
    }
}
