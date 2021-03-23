package com.csu.rpc.server.process;


import com.csu.rpc.server.process.processImpl.InvokeHandlerImpl;
import com.csu.rpc.utils.SingletonFactory;

/**
 * @Author Chen Yu
 * @Date 2021/3/23 20:02
 */
public interface InvokeHandler {
    InvokeHandler INSTANCE = SingletonFactory.getInstance(InvokeHandlerImpl.class);

    Object invokeMethod(Object service, String methodName, Object[] args, Class<?>[] argTypes);
}
