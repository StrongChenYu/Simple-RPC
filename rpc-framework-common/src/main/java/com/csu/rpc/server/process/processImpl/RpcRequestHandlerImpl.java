package com.csu.rpc.server.process.processImpl;

import com.csu.rpc.bean.ServiceInfo;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.server.process.InvokeHandler;
import com.csu.rpc.server.process.RpcRequestHandler;
import com.csu.rpc.server.process.ServerProvider;

/**
 * @Author Chen Yu
 * @Date 2021/3/23 20:34
 */
public class RpcRequestHandlerImpl implements RpcRequestHandler {

    private final InvokeHandler invokeHandler = InvokeHandler.INSTANCE;
    private final ServerProvider serverProvider = ServerProvider.INSTANCE;

    @Override
    public Object handleRpcRequest(RpcRequest request) {
        ServiceInfo serviceInfo = serverProvider.obtainService(request.getServiceName());
        if (serviceInfo == null) System.out.println("2021.3.29 [没有找到服务不能调用]");
        Object res = invokeHandler.invokeMethod(serviceInfo.getServerImpl(),
                request.getMethodName(),
                request.getArgs(),
                request.getArgTypes());

        return res;
    }
}
