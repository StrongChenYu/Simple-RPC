package com.csu.rpc.client.handler;

import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Chen Yu
 * @Date 2021/4/19 13:46
 */
public class UnProcessRequestsManager {

    private Map<String, CompletableFuture<RpcResponse>> futureMap = new ConcurrentHashMap<>();

    public void addUnProcessRequest(String requestId, CompletableFuture<RpcResponse> future) {
        futureMap.put(requestId, future);
    }

    public void complete(RpcResponse response) {
        CompletableFuture<RpcResponse> future = futureMap.get(response.getRequestId());
        if (future != null) {
            future.complete(response);
        } else {
            throw new IllegalStateException();
        }
    }

}
