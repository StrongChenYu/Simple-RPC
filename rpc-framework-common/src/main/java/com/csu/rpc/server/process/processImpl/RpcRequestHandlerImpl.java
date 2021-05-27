package com.csu.rpc.server.process.processImpl;

import com.csu.rpc.bean.RpcServiceInfo;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import com.csu.rpc.server.process.RpcRequestHandler;
import com.csu.rpc.server.process.ServerProvider;
import com.csu.rpc.utils.SingletonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author Chen Yu
 * @Date 2021/3/23 20:34
 */
@Slf4j
public class RpcRequestHandlerImpl implements RpcRequestHandler {

    ServerProvider serverProvider = SingletonFactory.getInstance(ServerProviderImpl.class);

    @Override
    public RpcResponse handleRpcRequest(RpcRequest request) {

        String requestId = request.getRequestId();
        RpcServiceInfo serviceInfo = new RpcServiceInfo(request.getServiceName(), request.getGroup(), request.getVersion());

        /**
         * 在本服务器上查找service
         * 找不到就不进行调用
         */
        Object serviceObject = serverProvider.obtainService(serviceInfo);
        if (serviceObject == null) {
            log.warn("服务端没有找到服务{}", request.getServiceName());
            return RpcResponse.SERVICE_NOT_FIND(requestId);
        }

        /**
         * 调用方法
         * 建议使用try catch
         * 这样能捕获到调用过程中的错误
         */
        Object res = null;
        try {
            res = invokeMethod(serviceObject,
                    request.getMethodName(),
                    request.getArgs(),
                    request.getArgTypes());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.error("Invoke rpc service {} fail", request.getServiceName());
            e.printStackTrace();
            return RpcResponse.INVOKE_ERROR(requestId);
        }

        log.info("Invoke rpc service {} successfully", request.getServiceName());
        return RpcResponse.SUCCESS(requestId, res);
    }

    public Object invokeMethod(Object service, String methodName, Object[] args, Class<?>[] argTypes) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object res = null;

        Method method = service.getClass().getMethod(methodName, argTypes);
        res = method.invoke(service, args);

        return res;
    }

}
