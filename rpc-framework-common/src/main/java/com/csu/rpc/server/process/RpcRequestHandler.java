package com.csu.rpc.server.process;

import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import com.csu.rpc.server.process.processImpl.RpcRequestHandlerImpl;
import com.csu.rpc.utils.SingletonFactory;

/**
 * @Author Chen Yu
 * @Date 2021/3/23 20:34
 */
public interface RpcRequestHandler {

    RpcRequestHandler INSTANCE = new RpcRequestHandlerImpl();

    RpcResponse handleRpcRequest(RpcRequest request);

}
