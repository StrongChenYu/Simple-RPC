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

    /**
     * 这里为什么会报循环错误？
     *
     * 因为SingletonFactory.getInstance会创建一个新对象
     * 但是新对象创建之时又会调用
     * SingletonFactory.getInstance
     * 循环调用
     */
    //private final InvokeHandler invokeHandler = SingletonFactory.getInstance(InvokeHandlerImpl.class);

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
